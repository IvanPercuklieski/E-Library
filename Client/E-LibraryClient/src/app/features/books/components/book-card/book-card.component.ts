import { Component, Input, OnInit } from '@angular/core';
import { IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonBadge } from '@ionic/angular/standalone';
import { CommonModule } from '@angular/common';

export interface Book {
	id: number;
	title: string;
	authorName: string;
	genreNames: string[];
	description: string;
	totalBookCopies: number;
	availableBookCopies: number;
	pubDate: string;
}

@Component({
	selector: 'app-book-card',
	templateUrl: './book-card.component.html',
	styleUrls: ['./book-card.component.scss'],
	imports: [IonCardTitle, IonCard, IonCardHeader, IonCardContent, IonBadge, CommonModule],
})
export class BookCardComponent implements OnInit {
	@Input() book!: Book;

	constructor() {}

	ngOnInit() {}

	isAvailable(): boolean {
		return this.book.availableBookCopies > 0;
	}
}
