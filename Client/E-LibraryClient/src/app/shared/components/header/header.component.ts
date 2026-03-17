import { Component, computed, inject, input, signal, ViewChild } from '@angular/core';
import { AlertController, IonicModule, PopoverController } from '@ionic/angular';
import { Router } from '@angular/router';
import { Auth } from 'src/app/features/auth/services/auth';
import { ToastService } from '../../services/toast';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: ['./header.component.scss'],
	imports: [IonicModule],
})
export class HeaderComponent {
	private authService = inject(Auth);
	private toastsService = inject(ToastService);
	private alertConteller = inject(AlertController);
	private router = inject(Router);

	title = input<string>('Library App');
	isLoggedIn = computed(() => this.authService.isAuthenticated());
	currentUser: any = this.authService.getCurrentUser();
	isProfileMenuOpen = signal(false);
	@ViewChild('profileMenuPopover') profileMenuPopover!: HTMLIonPopoverElement;

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
						this.toastsService.show('Logged out successfully');
					},
				},
			],
		});

		await alert.present();
	}

	onLoginBtnClick() {
		this.router.navigate(['/auth/login']);
	}

	onRegisterBtnClick() {
		this.router.navigate(['/auth/register']);
	}

	openProfileMenuPopover(e: Event) {
		this.profileMenuPopover.event = e;
		this.isProfileMenuOpen.set(true);
	}

	onTitleClick() {
		this.router.navigate(['/home']);
	}

	onAdminPanelClick() {
		this.router.navigate(['/admin-panel']);
	}

	onSeatingClick() {
		this.router.navigate(['/seating']);
	}
}
