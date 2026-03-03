import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { of, switchMap, tap } from 'rxjs';
import { StorageService } from 'src/app/core/services/storage';
import { TokenDecode } from 'src/app/core/services/token-decode';

export interface loginCredentials {
	username: string;
	password: string;
}

@Injectable({
  providedIn: 'root'
})
export class Auth {
	private storageService = inject(StorageService);
	private http = inject(HttpClient);
	private tokenDecode = inject(TokenDecode);

	isAuthenticated = signal(!!this.storageService.get('currentUser'));

	getCurrentUser() {
		return this.storageService.get('currentUser');
	}

	login(loginCredentials: loginCredentials) {
		return this.http.post<any>('employee/login', loginCredentials)
			.pipe(
				switchMap(resp => {
					if(!resp || !resp.token) {
						throw new Error('Invalid login response');
					}
					let user = {
						username: loginCredentials.username,
						token: resp.token,
						role: this.tokenDecode.decodeJWTToken(resp.token).roles[0].authority // Go proveriv samo za admin
					}
					this.storageService.set('currentUser', user);
					this.isAuthenticated.set(true);
					return of(true);
				}),
				
			)
	}

	register(registerData: any) {
		return this.http.post<any>('employee/register', registerData);
	}

	logout() {
		this.storageService.remove('currentUser');
		this.isAuthenticated.set(false);
	}
}
