export interface EmployeeSession {
	username: string;
	token: string;
	role: string;
}

export interface Author {
	id: number;
	name: string;
}

export interface Genre {
	id: number;
	name: string;
}

export interface LibraryUser {
	id: number;
	name: string;
	surname: string;
	email: string;
}

export interface Book {
	id: number;
	title: string;
	authorId?: number;
	authorName: string;
	genreIds?: number[];
	genreNames: string[];
	pubDate: string;
	description: string;
	numBooks?: number;
	totalBookCopies: number;
	availableBookCopies: number;
}

export interface CreateOrUpdateBookDto {
	title: string;
	authorId: number;
	genreIds: number[];
	pubDate: string;
	description: string;
	numBooks: number;
}

export interface GenreDto {
	name: string;
}

export interface AuthorDto {
	name: string;
}

export interface LibraryUserDto {
	name: string;
	surname: string;
	email: string;
}
