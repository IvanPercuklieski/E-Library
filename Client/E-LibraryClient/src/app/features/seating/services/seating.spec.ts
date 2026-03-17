import { TestBed } from '@angular/core/testing';

import { Seating } from './seating';

describe('Seating', () => {
	let service: Seating;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(Seating);
	});

	it('should be created', () => {
		expect(service).toBeTruthy();
	});
});
