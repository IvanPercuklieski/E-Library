import { roleGuard } from './core/guards/role-guard';
import { Routes } from '@angular/router';

export const routes: Routes = [
	{
		path: '',
		redirectTo: 'home',
		pathMatch: 'full',
	},
	{
		path: 'home',
		loadChildren: () => import('./features/home-page/home-page.routes').then((m) => m.HOME_PAGE_ROUTES),
	},
	{
		path: 'books',
		loadChildren: () => import('./features/books/books.routes').then((m) => m.BOOKS_ROUTES),
	},
	{
		path: 'resources',
		loadChildren: () => import('./features/admin-panel/admin-panel.routes').then((m) => m.ADMIN_PANEL_ROUTES),
		canActivate: [roleGuard(['ADMIN', 'BASIC'])],
	},
	{
		path: 'admin-panel',
		redirectTo: 'resources',
		pathMatch: 'full',
	},
	{
		path: 'seating',
		loadChildren: () => import('./features/seating/seating.routes').then((m) => m.SEATING_ROUTES),
	},
	{
		path: '**',
		redirectTo: 'home',
		pathMatch: 'full',
	},
];
