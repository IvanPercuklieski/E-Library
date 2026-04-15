import { Routes } from '@angular/router';

export const SEATING_ROUTES: Routes = [
	{
		path: '',
		loadComponent: () =>
			import('./components/seating-view/seating-view.component').then((m) => m.SeatingViewComponent),
	},
	{
		path: ':id',
		loadComponent: () =>
			import('./components/seating-grid/seating-grid.component').then((m) => m.SeatingGridComponent),
	},
];
