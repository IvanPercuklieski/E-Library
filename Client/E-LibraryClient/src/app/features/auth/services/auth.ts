import { inject, Injectable } from '@angular/core';
import { StorageService } from 'src/app/core/services/storage';
@Injectable({
  providedIn: 'root'
})
export class Auth {
	private storageService = inject(StorageService);

	isAuthenticated(): boolean {
		return !!this.storageService.get('currentUser');
	}
}
