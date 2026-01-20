import { Routes } from "@angular/router";

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: "**",
        redirectTo: 'home'
    },
    {
        path: 'auth',
        loadChildren: () => import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES)
    },
    {
        path: 'admin-panel',
        loadChildren: () => import('./features/admin-panel/admin-panel.routes').then(m => m.ADMIN_PANEL_ROUTES)
    }
    
]