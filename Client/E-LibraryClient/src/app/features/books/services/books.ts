import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Author, Book, Genre } from 'src/app/core/models/modeli';

@Injectable({
	providedIn: 'root',
})
export class Books {}

@Injectable({
	providedIn: 'root',
})
export class BooksService {
	private http = inject(HttpClient);

	getBooks() {
		return this.http.get<Book[]>('api/books');
	}

	getBookById(id: number) {
		return this.http.get<Book>(`api/books/get/${id}`);
	}

	getAuthors() {
		return this.http.get<Author[]>('authors');
	}

	getGenres() {
		return this.http.get<Genre[]>('api/genres');
	}
}
