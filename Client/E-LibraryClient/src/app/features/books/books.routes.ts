import { Routes } from '@angular/router';

export const BOOKS_ROUTES: Routes = [
	{
		path: '',
		loadComponent: () =>
			import('./components/books-search/books-search.component').then((m) => m.BooksSearchComponent),
	},
];
