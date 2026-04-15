import { LibraryUser } from './modeli';

export interface Room {
	id: number;
	name: string;
	location: string;
	numSeats: number;
	availableSeatsCount: number;
	seats?: Seat[];
}

export interface Seat {
	id: number;
	seatNumber: number;
	taken: boolean;
	room?: Room;
	user?: LibraryUser | null;
}

export interface UpdateSeatDto {
	isTaken: boolean;
	userId?: number;
}

export interface RoomDto {
	name: string;
	location: string;
	numSeats: number;
}
