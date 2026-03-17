import { Component, OnInit, signal } from '@angular/core';
import { inject } from '@angular/core';
import { Auth } from '../../services/auth';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {
	IonContent,
	IonCard,
	IonCardHeader,
	IonCardTitle,
	IonCardContent,
	IonItem,
	IonInput,
	IonText,
	IonButton,
	IonHeader,
} from '@ionic/angular/standalone';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';

@Component({
	selector: 'app-register',
	templateUrl: './register.component.html',
	styleUrls: ['./register.component.scss'],
	imports: [
		IonHeader,
		IonButton,
		IonText,
		IonInput,
		IonItem,
		IonCardContent,
		IonCardTitle,
		IonCardHeader,
		IonCard,
		IonContent,
		ReactiveFormsModule,
		HeaderComponent,
	],
})
export class RegisterComponent {
	private authService = inject(Auth);
	private router = inject(Router);

	registerForm = new FormGroup({
		username: new FormControl('', {
			validators: [Validators.required],
		}),
		password: new FormControl('', {
			validators: [Validators.required],
		}),
		email: new FormControl('', {
			validators: [Validators.required, Validators.email],
		}),
	});

	isSubmitting = signal(false);
	errorMessage = signal<string | null>(null);

	onSubmit() {
		if (this.registerForm.valid) {
			const registerData = {
				username: this.registerForm.value.username!,
				password: this.registerForm.value.password!,
				email: this.registerForm.value.email!,
			};

			this.authService.register(registerData).subscribe({
				next: () => {
					this.isSubmitting.set(false);
					this.registerForm.reset();
					this.router.navigate(['/auth/login']);
				},
				error: (err) => {
					this.isSubmitting.set(false);
					this.errorMessage.set(err.error || 'Registration failed. Please try again.');
				},
			});
		}
	}

	goToLogin() {
		this.registerForm.reset();
		this.router.navigate(['/auth/login']);
	}
}
