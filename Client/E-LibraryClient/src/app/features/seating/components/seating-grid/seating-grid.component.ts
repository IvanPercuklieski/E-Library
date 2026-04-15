import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonHeader, IonContent, IonButton, IonModal, IonToolbar, IonTitle, IonButtons, IonList, IonItem, IonLabel, IonInput, IonFooter } from '@ionic/angular/standalone';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { SeatingService } from '../../services/seating';
import { ActivatedRoute } from '@angular/router';
import { Room, Seat } from '../../../../core/models/seating.models';
import { LibraryUser } from '../../../../core/models/modeli';
import { Auth } from '../../../auth/services/auth';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
	selector: 'app-seating-grid',
	templateUrl: './seating-grid.component.html',
	styleUrls: ['./seating-grid.component.scss'],
	imports: [
		CommonModule,
		IonHeader, IonContent, IonButton, IonModal, IonToolbar, IonTitle, IonButtons, 
		IonList, IonItem, IonLabel, IonInput, IonFooter, HeaderComponent,
		FormsModule, ReactiveFormsModule
	],
})
export class SeatingGridComponent implements OnInit {
	private seatingService = inject(SeatingService);
	private route = inject(ActivatedRoute);
	private authService = inject(Auth);

	roomId: number = 0;
	roomDetails = signal<Room | null>(null);
	seats = signal<Seat[]>([]);
	users = signal<LibraryUser[]>([]);
	
	// Layout
	gridCols = signal<number>(0);

	// Modal State
	isManageModalOpen = signal(false);
	
	// Form Search & Selection
	userSearchQuery = signal('');
	showUserDropdown = signal(false);
	
	selectedSeatId = signal<number | null>(null);

	manageForm = new FormGroup({
		userId: new FormControl<number | null>(null),
		seatNumber: new FormControl<number | null>(null, Validators.required)
	});

	// For conditionally showing tooltip and manage controls
	isAdminOrBasic = signal(false);

	filteredUsers = computed(() => {
		const q = this.userSearchQuery().trim().toLowerCase();
		if (!q) return this.users().slice(0, 50);
		return this.users().filter(u => 
			`${u.name} ${u.surname}`.toLowerCase().includes(q) || u.email.toLowerCase().includes(q)
		).slice(0, 50);
	});

	ngOnInit() {
		this.isAdminOrBasic.set(this.authService.hasAnyRole(['ADMIN', 'BASIC']));
		
		const idParam = this.route.snapshot.paramMap.get('id');
		if (idParam) {
			this.roomId = +idParam;
			this.loadRoomSeats();
			this.loadUsers();
		}
	}

	loadRoomSeats() {
		this.seatingService.getRoomSeats(this.roomId).subscribe(data => {
			this.seats.set(data);
			if (data.length > 0 && data[0].room) {
				this.roomDetails.set(data[0].room);
				let numSeats = data[0].room.numSeats;
				this.gridCols.set(Math.round(Math.sqrt(numSeats)));
			}
		});
	}

	loadUsers() {
		if (this.isAdminOrBasic()) {
			this.seatingService.getUsers().subscribe(users => {
				this.users.set(users);
			});
		}
	}

	get isSelectedSeatTaken(): boolean {
		const sNum = this.manageForm.get('seatNumber')?.value;
		if (sNum === null) return false;
		const seat = this.seats().find(s => s.seatNumber === sNum);
		return seat?.taken || false;
	}

	get currentMappedSeatId(): number | null {
		const sNum = this.manageForm.get('seatNumber')?.value;
		if (sNum === null) return null;
		const seat = this.seats().find(s => s.seatNumber === sNum);
		return seat?.id || null;
	}

	openManageModal(seatId?: number) {
		if (!this.isAdminOrBasic()) return;

		this.manageForm.reset();
		this.userSearchQuery.set('');
		this.showUserDropdown.set(false);

		if (seatId) {
			this.selectedSeatId.set(seatId);
			const targetSeat = this.seats().find(s => s.id === seatId);
			if (targetSeat) {
				this.manageForm.patchValue({ seatNumber: targetSeat.seatNumber });
			}
			if (targetSeat && targetSeat.taken && targetSeat.user) {
				this.manageForm.patchValue({ userId: targetSeat.user.id });
				this.userSearchQuery.set(`${targetSeat.user.name} ${targetSeat.user.surname}`);
			}
		} else {
			this.selectedSeatId.set(null);
		}

		this.isManageModalOpen.set(true);
	}

	onUserSearchInput(event: any) {
		this.userSearchQuery.set(event.detail.value ?? '');
		this.showUserDropdown.set(true);
		
		// If they clear the text, clear the selection
		if (!this.userSearchQuery()) {
			this.manageForm.patchValue({ userId: null });
		}
	}

	selectUser(user: LibraryUser) {
		this.manageForm.patchValue({ userId: user.id });
		this.userSearchQuery.set(`${user.name} ${user.surname}`);
		this.showUserDropdown.set(false);
	}

	releaseCurrentSeat() {
		const sId = this.currentMappedSeatId;
		if (sId) {
			this.seatingService.releaseSeat(sId).subscribe({
				next: () => {
					this.loadRoomSeats();
					this.closeModal();
				},
				error: (err) => console.error(err)
			});
		}
	}

	saveSeat() {
		const fv = this.manageForm.value;
		const targetSeatId = this.currentMappedSeatId;
		if (fv.seatNumber !== null && fv.userId && targetSeatId) {
			this.seatingService.reserveSeat(targetSeatId, fv.userId).subscribe({
				next: () => {
					this.loadRoomSeats();
					this.closeModal();
				},
				error: (err) => console.error(err)
			});
		}
	}

	closeModal() {
		this.isManageModalOpen.set(false);
	}
}
