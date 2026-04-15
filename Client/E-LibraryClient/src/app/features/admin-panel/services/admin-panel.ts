import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import {
	Author,
	AuthorDto,
	Book,
	CreateOrUpdateBookDto,
	Employee,
	EmployeeRegisterDto,
	Genre,
	GenreDto,
	LibraryUser,
	LibraryUserDto,
    Borrowing,
    BorrowingDto,
} from 'src/app/core/models/modeli';
import { Room, RoomDto } from 'src/app/core/models/seating.models';

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

	getBookCopies(bookId: number) {
		return this.http.get<any[]>(`api/book-copies/by-book/${bookId}`);
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
		return this.http.post<Author>('authors/create-author', payload);
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

	getUserById(userId: number) {
		return this.http.get<LibraryUser>(`api/user/get/${userId}`);
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

	getEmployees() {
		return this.http.get<Employee[]>('api/employee/all');
	}

	createEmployee(payload: EmployeeRegisterDto) {
		return this.http.post<Employee>('api/employee/register', payload);
	}

	getBorrowings() {
		return this.http.get<Borrowing[]>('api/borrowings/getAll');
	}

	createBorrowing(payload: BorrowingDto) {
		return this.http.post<Borrowing>('api/borrowings/create', payload);
	}

	updateBorrowing(borrowingId: number, payload: BorrowingDto) {
		return this.http.put<Borrowing>(`api/borrowings/update/${borrowingId}`, payload);
	}

	deleteBorrowing(borrowingId: number) {
		return this.http.delete<void>(`api/borrowings/delete/${borrowingId}`);
	}

	getRooms() {
		return this.http.get<Room[]>('api/rooms');
	}

	createRoom(payload: RoomDto) {
		return this.http.post<Room>('api/rooms/create-room', payload);
	}

	updateRoom(roomId: number, payload: RoomDto) {
		return this.http.put<Room>(`api/rooms/update-room/${roomId}`, payload);
	}

	deleteRoom(roomId: number) {
		return this.http.delete<void>(`api/rooms/delete/${roomId}`);
	}
}
