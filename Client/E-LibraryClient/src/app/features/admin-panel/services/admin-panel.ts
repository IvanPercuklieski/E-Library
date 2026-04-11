import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
	Author,
	AuthorDto,
	Book,
	CreateOrUpdateBookDto,
	Genre,
	GenreDto,
	LibraryUser,
	LibraryUserDto,
} from 'src/app/core/models/modeli';

@Injectable({
	providedIn: 'root',
})
export class AdminPanel {}

@Injectable({
	providedIn: 'root',
})
export class ResourceManagerService {
	private http = inject(HttpClient);

	getBooks() {
		return this.http.get<Book[]>('api/books');
	}

	createBook(payload: CreateOrUpdateBookDto) {
		return this.http.post<Book>('api/books/create', payload);
	}

	updateBook(bookId: number, payload: CreateOrUpdateBookDto) {
		return this.http.put<Book>(`api/books/update/${bookId}`, payload);
	}

	deleteBook(bookId: number) {
		return this.http.delete<void>(`api/books/delete/${bookId}`);
	}

	getGenres() {
		return this.http.get<Genre[]>('api/genres');
	}

	createGenre(payload: GenreDto) {
		return this.http.post<Genre>('api/genres/create', payload);
	}

	updateGenre(genreId: number, payload: GenreDto) {
		return this.http.put<Genre>(`api/genres/update/${genreId}`, payload);
	}

	deleteGenre(genreId: number) {
		return this.http.delete<void>(`api/genres/delete/${genreId}`);
	}

	getAuthors() {
		return this.http.get<Author[]>('authors');
	}

	createAuthor(payload: AuthorDto) {
		return this.http.post<Author>('authors/create', payload);
	}

	updateAuthor(authorId: number, payload: AuthorDto) {
		return this.http.put<Author>(`authors/update/${authorId}`, payload);
	}

	deleteAuthor(authorId: number) {
		return this.http.delete<void>(`authors/delete/${authorId}`);
	}

	getUsers() {
		return this.http.get<LibraryUser[]>('api/user/all');
	}

	createUser(payload: LibraryUserDto) {
		return this.http.post<LibraryUser>('api/user/add', payload);
	}

	updateUser(userId: number, payload: LibraryUserDto) {
		return this.http.put<LibraryUser>(`api/user/update/${userId}`, payload);
	}

	deleteUser(userId: number) {
		return this.http.delete<void>(`api/user/delete/${userId}`);
	}
}
