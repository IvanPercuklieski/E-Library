import { Component, computed, inject, input, signal, ViewChild } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AlertController, IonicModule, PopoverController } from '@ionic/angular';
import { Router } from '@angular/router';
import { Auth } from 'src/app/features/auth/services/auth';
import { ToastService } from '../../services/toast';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss'],
	imports: [IonicModule, ReactiveFormsModule],
})
export class HeaderComponent {
	private authService = inject(Auth);
	private toastsService = inject(ToastService);
	private alertConteller = inject(AlertController);
	private router = inject(Router);

	title = input<string>('Library App');
	isLoggedIn = computed(() => this.authService.isAuthenticated());
	currentUser = computed(() => this.authService.getCurrentUser());
	canAccessResources = computed(() => this.authService.hasAnyRole(['ADMIN', 'BASIC']));
	isProfileMenuOpen = signal(false);
	isLoginModalOpen = signal(false);
	loginError = signal<string | null>(null);
	isLoggingIn = signal(false);
	@ViewChild('profileMenuPopover') profileMenuPopover!: HTMLIonPopoverElement;

	loginForm = new FormGroup({
		username: new FormControl('', Validators.required),
		password: new FormControl('', Validators.required),
	});

	async onLogout() {
		const alert = await this.alertConteller.create({
			header: 'Logout',
			message: 'Are you sure you want to logout?',
			buttons: [
				{
					text: 'Cancel',
					role: 'cancel',
				},
				{
					text: 'Logout',
					handler: () => {
						this.authService.logout();
						this.isProfileMenuOpen.set(false);
						this.isLoginModalOpen.set(false);
						this.loginError.set(null);
						this.resetLoginForm();
						this.toastsService.show('Logged out successfully');
						this.router.navigate(['/home']);
					},
				},
			],
		});

		await alert.present();
	}

	onLoginBtnClick() {
		this.isProfileMenuOpen.set(false);
		this.loginError.set(null);
		this.resetLoginForm();
		this.isLoginModalOpen.set(true);
	}

	openProfileMenuPopover(e: Event) {
		this.profileMenuPopover.event = e;
		this.isProfileMenuOpen.set(true);
	}

	onTitleClick() {
		this.router.navigate(['/home']);
	}

	onAdminPanelClick() {
		this.router.navigate(['/resources']);
	}

	onSeatingClick() {
		this.router.navigate(['/books']);
	}

	onBooksClick() {
		this.router.navigate(['/seating']);
	}

	closeLoginModal() {
		this.isLoginModalOpen.set(false);
		this.loginError.set(null);
		this.resetLoginForm();
	}

	submitLogin() {
		if (this.loginForm.invalid) {
			this.loginError.set('Username and password are required.');
			return;
		}

		this.isLoggingIn.set(true);
		this.loginError.set(null);

		this.authService
			.login({
				username: this.loginForm.value.username ?? '',
				password: this.loginForm.value.password ?? '',
			})
			.subscribe({
				next: () => {
					this.isLoggingIn.set(false);
					this.isLoginModalOpen.set(false);
					this.resetLoginForm();
					this.toastsService.show('Logged in successfully');
				},
				error: () => {
					this.isLoggingIn.set(false);
					this.loginError.set('Login failed. Please check your credentials.');
				},
			});
	}

	private resetLoginForm() {
		this.loginForm.reset({
			username: '',
			password: '',
		});
	}
}
