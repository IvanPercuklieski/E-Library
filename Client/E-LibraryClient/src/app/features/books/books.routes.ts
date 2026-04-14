import { Routes } from '@angular/router';

export const BOOKS_ROUTES: Routes = [
	{
		path: '',
		loadComponent: () =>
			import('./components/books-search/books-search.component').then((m) => m.BooksSearchComponent),
	},
	{
		path: ':id',
		loadComponent: () =>
			import('./components/book-details/book-details.component').then((m) => m.BookDetailsComponent),
	},
];
