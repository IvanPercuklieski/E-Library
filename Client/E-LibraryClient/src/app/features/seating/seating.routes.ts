import { Routes } from "@angular/router";

export const SEATING_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./components/seating-view/seating-view.component').then(m => m.SeatingViewComponent)
    }
]