import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { StorageService } from '../services/storage';
import { catchError, throwError } from 'rxjs';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
	const apiUrl = 'http://localhost:8082/api/';
	const storageService = inject(StorageService);
	const token = storageService.get<any>('currentUser')?.token;

	const updatedReq = req.clone({
		url: `${apiUrl}${req.url}`,
		headers: token ? req.headers.set('Authorization', `Bearer ${token}`) : req.headers,
	});

	return next(updatedReq).pipe(
		catchError((error: HttpErrorResponse) => {
			if (error.status === 401) {
				storageService.remove('currentUser');
			}
			return throwError(() => error);
		}),
	);
};
