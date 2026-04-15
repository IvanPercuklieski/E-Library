import { Routes } from '@angular/router';

export const LOANS_ROUTES: Routes = [
	{
		path: '',
		loadComponent: () => import('./components/loans/loans.component').then((m) => m.LoansComponent),
	},
];
