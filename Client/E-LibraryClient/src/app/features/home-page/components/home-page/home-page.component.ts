import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { IonHeader, IonContent, IonButton } from '@ionic/angular/standalone';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
	selector: 'app-home-page',
	templateUrl: './home-page.component.html',
	styleUrls: ['./home-page.component.scss'],
	imports: [IonHeader, IonContent, IonButton, HeaderComponent, CommonModule],
})
export class HomePageComponent implements OnInit {
	private router = inject(Router);

	ngOnInit() {
		return;
	}

	goToBooks() {
		this.router.navigate(['/books']);
	}

	goToRooms() {
		this.router.navigate(['/seating']);
	}
}
