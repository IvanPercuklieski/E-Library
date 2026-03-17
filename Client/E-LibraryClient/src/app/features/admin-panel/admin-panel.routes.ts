import { Routes } from '@angular/router';

export const ADMIN_PANEL_ROUTES: Routes = [
	{
		path: '',
		loadComponent: () =>
			import('./components/admin-panel/admin-panel.component').then((m) => m.AdminPanelComponent),
	},
];
