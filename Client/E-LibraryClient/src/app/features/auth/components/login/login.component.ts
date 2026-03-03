import { Component, inject, OnInit, OnDestroy, signal } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { IonCard, IonContent, IonCardTitle, IonCardContent, IonItem, IonLabel, IonInput, IonButton, IonCardHeader, IonText, IonHeader } from "@ionic/angular/standalone";
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { HeaderComponent } from "src/app/shared/components/header/header.component";
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [IonText, IonCardHeader, IonButton, ReactiveFormsModule, IonInput, IonItem, IonCardContent, IonCardTitle, IonContent, IonCard, IonHeader, HeaderComponent],
})
export class LoginComponent{
private authService = inject(Auth);
	private router = inject(Router);

    errorMessage = signal<string | null>(null);
    isSubmitting = signal(false);

	loginForm = new FormGroup({
		username: new FormControl('', {
			validators: [Validators.required],
		}),
		password: new FormControl('', {
			validators: [Validators.required],
		}),
	});

	onSubmit() {
		if (this.loginForm.valid) {
			const loginCredentials = {
				username: this.loginForm.value.username!,
				password: this.loginForm.value.password!,
			};

            this.isSubmitting.set(true);

			this.authService.login(loginCredentials).subscribe({
				next: () => {
                    this.isSubmitting.set(false);
                    this.loginForm.reset();
					this.router.navigate(['/home']);
				},
				error: (err) => {
                    this.isSubmitting.set(false);
                    this.errorMessage.set(err.error || 'Login failed. Please check your credentials and try again.');
				}
			})

		}
	}

    goToRegister() {
        this.loginForm.reset();
        this.router.navigate(['/auth/register']);
    }
}
  