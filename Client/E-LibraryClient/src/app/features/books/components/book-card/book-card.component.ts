import { Component, Input, OnInit } from '@angular/core';
import { IonCard, IonCardHeader, IonCardTitle, IonCardContent } from '@ionic/angular/standalone';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Book } from 'src/app/core/models/modeli';

@Component({
	selector: 'app-book-card',
	templateUrl: './book-card.component.html',
	styleUrls: ['./book-card.component.scss'],
	imports: [IonCardTitle, IonCard, IonCardHeader, IonCardContent, CommonModule, RouterModule],
})
export class BookCardComponent implements OnInit {
	@Input() book!: Book;

	constructor() {}

	ngOnInit() {}

	isAvailable(): boolean {
		return this.book.availableBookCopies > 0;
	}
}
