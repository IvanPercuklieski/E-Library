import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from 'src/app/features/auth/services/auth';

export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
	return () => {
		const authService = inject(Auth);
		const router = inject(Router);

		if (!authService.isAuthenticated()) {
			return router.createUrlTree(['/home']);
		}

		if (authService.hasAnyRole(allowedRoles)) {
			return true;
		}

		return router.createUrlTree(['/home']);
	};
};