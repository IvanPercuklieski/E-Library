import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Room, Seat, UpdateSeatDto } from '../../../core/models/seating.models';
import { LibraryUser } from '../../../core/models/modeli';

@Injectable({
	providedIn: 'root',
})
export class SeatingService {
	private http = inject(HttpClient);

	getRooms() {
		return this.http.get<Room[]>('api/rooms');
	}

	getRoomSeats(roomId: number) {
		return this.http.get<Seat[]>(`api/rooms/${roomId}/seats`);
	}

	releaseSeat(seatId: number) {
		return this.http.post(`api/seats/${seatId}/release`, {}, { responseType: 'text' });
	}

	reserveSeat(seatId: number, userId: number) {
		return this.http.post(`api/seats/${seatId}/reserve?userId=${userId}`, {}, { responseType: 'text' });
	}

	getUsers() {
		return this.http.get<LibraryUser[]>('api/user/all');
	}
}
