import { Component, OnInit } from '@angular/core';
import { IonHeader } from '@ionic/angular/standalone';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';

@Component({
	selector: 'app-seating-view',
	templateUrl: './seating-view.component.html',
	styleUrls: ['./seating-view.component.scss'],
	imports: [IonHeader, HeaderComponent],
})
export class SeatingViewComponent implements OnInit {
	constructor() {}

	ngOnInit() {}
}
