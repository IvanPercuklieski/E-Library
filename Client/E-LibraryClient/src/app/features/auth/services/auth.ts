import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { of, switchMap, tap } from 'rxjs';
import { EmployeeSession } from 'src/app/core/models/modeli';
import { StorageService } from 'src/app/core/services/storage';
import { TokenDecode } from 'src/app/core/services/token-decode';

export interface loginCredentials {
	username: string;
	password: string;
}

@Injectable({
	providedIn: 'root',
})
export class Auth {
	private storageService = inject(StorageService);
	private http = inject(HttpClient);
	private tokenDecode = inject(TokenDecode);

	isAuthenticated = signal(!!this.storageService.get('currentUser'));

	getCurrentUser(): EmployeeSession | null {
		return this.storageService.get<EmployeeSession>('currentUser') ?? null;
	}

	getCurrentRole(): string | null {
		return this.getCurrentUser()?.role ?? null;
	}

	hasAnyRole(roles: string[]): boolean {
		const role = this.getCurrentRole();
		if (!role) {
			return false;
		}

		return roles.includes(role);
	}

	login(loginCredentials: loginCredentials) {
		return this.http.post<any>('api/employee/login', loginCredentials).pipe(
			switchMap((resp) => {
				if (!resp || !resp.token) {
					throw new Error('Invalid login response');
				}
				const user: EmployeeSession = {
					username: loginCredentials.username,
					token: resp.token,
					role: this.tokenDecode.decodeJWTToken(resp.token).roles[0].authority,
				};
				this.storageService.set('currentUser', user);
				this.isAuthenticated.set(true);
				return of(true);
			}),
		);
	}

	register(registerData: any) {
		return this.http.post<any>('api/employee/register', registerData);
	}

	logout() {
		this.storageService.remove('currentUser');
		this.isAuthenticated.set(false);
	}
}
