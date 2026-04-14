import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { Book } from 'src/app/core/models/modeli';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { BooksService } from '../../services/books';

@Component({
	selector: 'app-book-details',
	templateUrl: './book-details.component.html',
	styleUrls: ['./book-details.component.scss'],
	imports: [CommonModule, IonicModule, HeaderComponent, RouterModule],
})
export class BookDetailsComponent implements OnInit {
	private route = inject(ActivatedRoute);
	private booksService = inject(BooksService);

	book: Book | null = null;
	isLoading = true;

	ngOnInit() {
		const bookId = Number(this.route.snapshot.paramMap.get('id'));
		
		if (bookId) {
			this.booksService.getBookById(bookId).subscribe({
				next: (book) => {
					this.book = book;
					this.isLoading = false;
				},
				error: (error) => {
					console.error('Error fetching book details:', error);
					this.isLoading = false;
				}
			});
		} else {
			this.isLoading = false;
		}
	}
}
