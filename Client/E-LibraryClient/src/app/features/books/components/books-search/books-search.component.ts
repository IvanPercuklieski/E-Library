import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Author, Book, Genre } from 'src/app/core/models/modeli';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { BookCardComponent } from '../book-card/book-card.component';
import { BooksService } from '../../services/books';

@Component({
	selector: 'app-books-search',
	templateUrl: './books-search.component.html',
	styleUrls: ['./books-search.component.scss'],
	imports: [CommonModule, IonicModule, HeaderComponent, BookCardComponent],
})
export class BooksSearchComponent implements OnInit {
	private booksService = inject(BooksService);

	allBooks = signal<Book[]>([]);
	authors = signal<Author[]>([]);
	genres = signal<Genre[]>([]);
	isLoading = signal(false);
	searchQuery = signal('');
	selectedAuthorIds = signal<number[]>([]);
	selectedGenreIds = signal<number[]>([]);

	filteredBooks = computed(() => {
		const query = this.searchQuery().trim().toLowerCase();
		const selectedAuthors = this.selectedAuthorIds();
		const selectedGenres = this.selectedGenreIds();

		return this.allBooks().filter((book) => {
			const matchesQuery =
				!query ||
				book.title.toLowerCase().includes(query) ||
				book.authorName.toLowerCase().includes(query) ||
				book.description.toLowerCase().includes(query) ||
				book.genreNames.some((genre) => genre.toLowerCase().includes(query));

			const matchesAuthor =
				selectedAuthors.length === 0 || (!!book.authorId && selectedAuthors.includes(book.authorId));
			const matchesGenre =
				selectedGenres.length === 0 ||
				selectedGenres.some(
					(selectedGenreId) =>
						(book.genreIds ?? []).includes(selectedGenreId) || this.hasGenreNameMatch(book, selectedGenreId),
				);

			return matchesQuery && matchesAuthor && matchesGenre;
		});
	});

	ngOnInit() {
		this.loadPageData();
	}

	onSearchChange(event: CustomEvent) {
		this.searchQuery.set(event.detail.value ?? '');
	}

	onAuthorChange(event: CustomEvent) {
		const values = (event.detail.value ?? []) as Array<number | string>;
		this.selectedAuthorIds.set(values.map((value) => Number(value)));
	}

	onGenreChange(event: CustomEvent) {
		const values = (event.detail.value ?? []) as Array<number | string>;
		this.selectedGenreIds.set(values.map((value) => Number(value)));
	}

	clearFilters() {
		this.searchQuery.set('');
		this.selectedAuthorIds.set([]);
		this.selectedGenreIds.set([]);
	}

	getGenreLabel(genre: Genre): string {
		return (genre as any).title ?? (genre as any).name ?? (genre as any).name ?? '';
	}

	private loadPageData() {
		this.isLoading.set(true);
		forkJoin({
			books: this.booksService.getBooks().pipe(catchError(() => of([] as Book[]))),
			authors: this.booksService.getAuthors().pipe(catchError(() => of([] as Author[]))),
			genres: this.booksService.getGenres().pipe(catchError(() => of([] as Genre[]))),
		}).subscribe(({ books, authors, genres }) => {
			this.allBooks.set(books);
			this.authors.set(authors);
			this.genres.set(
				genres.map((genre) => ({
					...genre,
					title: this.getGenreLabel(genre),
				})),
			);
			this.isLoading.set(false);
		});
	}

	private hasGenreNameMatch(book: Book, selectedGenreId: number): boolean {
		const selectedGenre = this.genres().find((genre) => genre.id === selectedGenreId);
		if (!selectedGenre) {
			return false;
		}

		return book.genreNames.includes(this.getGenreLabel(selectedGenre));
	}
}
