import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { IonHeader, IonContent } from '@ionic/angular/standalone';
import { HomePage } from '../../services/home-page';
import { BookCardComponent, Book } from '../../../books/components/book-card/book-card.component';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'app-home-page',
	templateUrl: './home-page.component.html',
	styleUrls: ['./home-page.component.scss'],
	imports: [IonHeader, IonContent, HeaderComponent, BookCardComponent, CommonModule],
})
export class HomePageComponent implements OnInit {
	homePageService = inject(HomePage);
	books: Book[] = [];

	ngOnInit() {
		this.homePageService.getBooks()
			.subscribe((books: any) => {
				this.books = books;
			});
	}
}
