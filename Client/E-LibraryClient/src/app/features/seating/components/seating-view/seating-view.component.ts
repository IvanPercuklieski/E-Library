import { Component, OnInit, inject, signal } from '@angular/core';
import { IonHeader, IonContent, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardSubtitle, IonCardContent } from '@ionic/angular/standalone';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { SeatingService } from '../../services/seating';
import { Room } from '../../../../core/models/seating.models';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
	selector: 'app-seating-view',
	templateUrl: './seating-view.component.html',
	styleUrls: ['./seating-view.component.scss'],
	imports: [IonHeader, IonContent, HeaderComponent, IonGrid, IonRow, IonCol, IonCard, IonCardHeader, IonCardTitle, IonCardSubtitle, IonCardContent, RouterLink],
})
export class SeatingViewComponent implements OnInit {
	private seatingService = inject(SeatingService);
	rooms = signal<Room[]>([]);

	ngOnInit() {
		this.loadRooms();
	}

	loadRooms() {
		this.seatingService.getRooms().subscribe((res) => {
			this.rooms.set(res);
		});
	}
}
