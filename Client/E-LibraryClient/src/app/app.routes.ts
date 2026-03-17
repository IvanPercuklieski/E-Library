import { authGuard } from './core/guards/auth-guard';
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
		path: 'auth',
		loadChildren: () => import('./features/auth/auth.routes').then((m) => m.AUTH_ROUTES),
		canActivate: [authGuard],
	},
	{
		path: 'admin-panel',
		loadChildren: () => import('./features/admin-panel/admin-panel.routes').then((m) => m.ADMIN_PANEL_ROUTES),
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
