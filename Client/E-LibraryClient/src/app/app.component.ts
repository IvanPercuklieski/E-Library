import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { filter } from 'rxjs';

import { personCircle, optionsOutline, closeOutline, checkmarkOutline, closeCircle } from 'ionicons/icons';

addIcons({ personCircle, optionsOutline, closeOutline, checkmarkOutline, closeCircle });

@Component({
	selector: 'app-root',
	templateUrl: 'app.component.html',
	styleUrls: ['app.component.scss'],
	standalone: true,
	imports: [CommonModule, IonApp, IonRouterOutlet],
})
export class AppComponent {
	constructor(private router: Router) {
		this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe(() => {
			window.scrollTo({ top: 0, behavior: 'auto' });

			requestAnimationFrame(() => {
				const activeContent = document.querySelector('ion-content') as
					| (HTMLElement & { scrollToTop?: (duration?: number) => Promise<void> })
					| null;

				if (activeContent?.scrollToTop) {
					activeContent.scrollToTop(0);
				}
			});
		});
	}
}
