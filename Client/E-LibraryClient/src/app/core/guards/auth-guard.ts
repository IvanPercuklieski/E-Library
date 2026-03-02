import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from 'src/app/features/auth/services/auth';

export const authGuard: CanActivateFn = (route, state) => {
    const authService = inject(Auth);
    const router = inject(Router);

    const isAuthenticated = authService.isAuthenticated();

    if(isAuthenticated && (state.url === '/auth/login' || state.url === '/auth/register')) {
        return router.createUrlTree(['/home']);
    }

    if(isAuthenticated || state.url.startsWith('/auth')) {
        return true;
    }

    // Ova se deshava ako ne e authenticated
    if(state.url === '/auth/login' || state.url === '/auth/register') {
        return router.createUrlTree(['/auth/login']);
    }

    return true;
};
