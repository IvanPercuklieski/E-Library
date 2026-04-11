import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root',
})
export class HomePage {
	http = inject(HttpClient);

	getBooks() {
		return this.http.get('api/books');
	}
}
