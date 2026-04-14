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

	isFiltersOpen = signal(false);
	authorSearchQuery = signal('');
	genreSearchQuery = signal('');

	activeFiltersCount = computed(() => {
		return this.selectedAuthorIds().length + this.selectedGenreIds().length;
	});

	filteredAuthors = computed(() => {
		const query = this.authorSearchQuery().trim().toLowerCase();
		const selectedIds = this.selectedAuthorIds();

		if (!query) {
			return this.authors().filter((author) => selectedIds.includes(author.id));
		}
		
		return this.authors()
			.filter((author) => selectedIds.includes(author.id) || author.name.toLowerCase().includes(query))
			.slice(0, 50);
	});

	filteredGenres = computed(() => {
		const query = this.genreSearchQuery().trim().toLowerCase();
		const selectedIds = this.selectedGenreIds();

		if (!query) {
			return this.genres().filter((genre) => selectedIds.includes(genre.id));
		}
		
		return this.genres()
			.filter((genre) => selectedIds.includes(genre.id) || this.getGenreLabel(genre).toLowerCase().includes(query))
			.slice(0, 50);
	});

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

	openFilters() {
		this.isFiltersOpen.set(true);
	}

	closeFilters() {
		this.isFiltersOpen.set(false);
		this.authorSearchQuery.set('');
		this.genreSearchQuery.set('');
	}

	onAuthorSearchChange(event: CustomEvent) {
		this.authorSearchQuery.set(event.detail.value ?? '');
	}

	onGenreSearchChange(event: CustomEvent) {
		this.genreSearchQuery.set(event.detail.value ?? '');
	}

	toggleAuthor(authorId: number) {
		const current = this.selectedAuthorIds();
		if (current.includes(authorId)) {
			this.selectedAuthorIds.set(current.filter((id) => id !== authorId));
		} else {
			this.selectedAuthorIds.set([...current, authorId]);
		}
	}

	toggleGenre(genreId: number) {
		const current = this.selectedGenreIds();
		if (current.includes(genreId)) {
			this.selectedGenreIds.set(current.filter((id) => id !== genreId));
		} else {
			this.selectedGenreIds.set([...current, genreId]);
		}
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
