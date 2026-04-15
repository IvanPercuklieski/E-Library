import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { addIcons } from 'ionicons';
import { createOutline, trashOutline, checkmarkOutline, closeOutline, closeCircleOutline } from 'ionicons/icons';
import { catchError, forkJoin, of } from 'rxjs';
import { Borrowing, BorrowingDto, Book, LibraryUser } from 'src/app/core/models/modeli';
import { ResourceManagerService } from 'src/app/features/admin-panel/services/admin-panel';
import { ToastService } from 'src/app/shared/services/toast';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { Auth } from 'src/app/features/auth/services/auth';

@Component({
    selector: 'app-loans',
    templateUrl: './loans.component.html',
    styleUrls: ['./loans.component.scss'],
    imports: [CommonModule, ReactiveFormsModule, IonicModule, HeaderComponent],
})
export class LoansComponent implements OnInit {
    private resourcesService = inject(ResourceManagerService);
    private toast = inject(ToastService);
    private auth = inject(Auth);

    constructor() {
        addIcons({ createOutline, trashOutline, checkmarkOutline, closeOutline, closeCircleOutline });
    }

    rows = signal<Borrowing[]>([]);
    books = signal<Book[]>([]);
    users = signal<LibraryUser[]>([]);

    isLoading = signal(false);
    editorOpen = signal(false);
    editingId = signal<number | null>(null);

    // filters
    searchQuery = signal('');
    borrowUserSearchQuery = signal('');
    borrowBookSearchQuery = signal('');
    showBorrowUserDropdown = signal(false);
    showBorrowBookDropdown = signal(false);
    borrowUserFilter = signal<number | null>(null);
    borrowBookFilter = signal<number | null>(null);
    dueDateFrom = signal<string | null>(null);
    dueDateTo = signal<string | null>(null);
    showOverdue = signal(false);

    // modal form search state
    formUserSearchQuery = signal('');
    formBookSearchQuery = signal('');
    showFormUserDropdown = signal(false);
    showFormBookDropdown = signal(false);

    borrowForm = new FormGroup({
        userId: new FormControl<number | null>(null, Validators.required),
        bookCopyId: new FormControl<number | null>(null, Validators.required),
        borrowedAt: new FormControl('', Validators.required),
        dueDate: new FormControl('', Validators.required),
    });

    filteredBorrowUsers = computed(() => {
        const q = this.borrowUserSearchQuery().trim().toLowerCase();
        if (!q) return this.users().slice(0, 50);
        return this.users().filter((u) => `${u.name} ${u.surname}`.toLowerCase().includes(q)).slice(0, 50);
    });

    filteredBorrowBooks = computed(() => {
        const q = this.borrowBookSearchQuery().trim().toLowerCase();
        if (!q) return this.books().slice(0, 50);
        return this.books().filter((b) => b.title.toLowerCase().includes(q)).slice(0, 50);
    });

    filteredFormUsers = computed(() => {
        const q = this.formUserSearchQuery().trim().toLowerCase();
        if (!q) return this.users().slice(0, 50);
        return this.users().filter((u) => `${u.name} ${u.surname}`.toLowerCase().includes(q)).slice(0, 50);
    });

    filteredFormBooks = computed(() => {
        const q = this.formBookSearchQuery().trim().toLowerCase();
        if (!q) return this.books().slice(0, 50);
        return this.books().filter((b) => b.title.toLowerCase().includes(q)).slice(0, 50);
    });

    filteredRows = computed(() => {
        let rows = this.rows().slice();
        const q = this.searchQuery().trim().toLowerCase();
        if (q) {
            rows = rows.filter((r) => {
                const user = this.users().find((u) => u.id === r.userId);
                const userText = user ? `${user.name} ${user.surname}` : '';
                return (
                    String(r.bookTitle).toLowerCase().includes(q) ||
                    userText.toLowerCase().includes(q) ||
                    String(r.borrowedAt).toLowerCase().includes(q) ||
                    String(r.dueDate).toLowerCase().includes(q)
                );
            });
        }

        const uid = this.borrowUserFilter();
        if (uid) rows = rows.filter((r) => r.userId === uid);

        const bid = this.borrowBookFilter();
        if (bid) rows = rows.filter((r) => r.bookCopyId === bid);

        const from = this.dueDateFrom() ? new Date(this.dueDateFrom() as string) : null;
        const to = this.dueDateTo() ? new Date(this.dueDateTo() as string) : null;
        if (from) rows = rows.filter((r) => new Date(r.dueDate) >= from);
        if (to) rows = rows.filter((r) => new Date(r.dueDate) <= to);

        if (this.showOverdue()) {
            const now = new Date();
            rows = rows.filter((r) => new Date(r.dueDate) < now);
        }

        return rows;
    });

    ngOnInit(): void {
        this.loadReferenceData();
        this.loadBorrowings();
    }

    private loadReferenceData() {
        this.resourcesService
            .getBooks()
            .pipe(catchError(() => of([])))
            .subscribe((books) => this.books.set(books));

        this.resourcesService
            .getUsers()
            .pipe(catchError(() => of([])))
            .subscribe((users) => this.users.set(users));
    }

    private loadBorrowings() {
        this.isLoading.set(true);
        this.resourcesService
            .getBorrowings()
            .pipe(catchError(() => of([])))
            .subscribe((rows) => {
                this.rows.set(rows);
                this.isLoading.set(false);
            });
    }

    onSearchChange(event: CustomEvent) {
        this.searchQuery.set(event.detail.value ?? '');
    }

    onBorrowUserSearchChange(event: CustomEvent) {
        this.borrowUserSearchQuery.set(event.detail.value ?? '');
        this.showBorrowUserDropdown.set(true);
    }

    onBorrowBookSearchChange(event: CustomEvent) {
        this.borrowBookSearchQuery.set(event.detail.value ?? '');
        this.showBorrowBookDropdown.set(true);
    }

    hideBorrowUserDropdown() {
        setTimeout(() => this.showBorrowUserDropdown.set(false), 200);
    }

    hideBorrowBookDropdown() {
        setTimeout(() => this.showBorrowBookDropdown.set(false), 200);
    }

    onFormUserSearchChange(event: CustomEvent) {
        this.formUserSearchQuery.set(event.detail.value ?? '');
        this.showFormUserDropdown.set(true);
    }

    onFormBookSearchChange(event: CustomEvent) {
        this.formBookSearchQuery.set(event.detail.value ?? '');
        this.showFormBookDropdown.set(true);
    }

    hideFormUserDropdown() {
        setTimeout(() => this.showFormUserDropdown.set(false), 200);
    }

    hideFormBookDropdown() {
        setTimeout(() => this.showFormBookDropdown.set(false), 200);
    }

    selectBorrowUser(id: number | null) {
        this.borrowUserFilter.set(id);
        if (id) {
            const u = this.users().find((x) => x.id === id);
            this.borrowUserSearchQuery.set(u ? `${u.name} ${u.surname}` : '');
        } else {
            this.borrowUserSearchQuery.set('');
        }
        this.showBorrowUserDropdown.set(false);
    }

    selectBorrowBook(id: number | null) {
        this.borrowBookFilter.set(id);
        if (id) {
            const b = this.books().find((x) => x.id === id);
            this.borrowBookSearchQuery.set(b ? b.title : '');
        } else {
            this.borrowBookSearchQuery.set('');
        }
        this.showBorrowBookDropdown.set(false);
    }

    clearBorrowUser(event?: Event) {
        if (event) event.stopPropagation();
        this.selectBorrowUser(null);
    }

    clearBorrowBook(event?: Event) {
        if (event) event.stopPropagation();
        this.selectBorrowBook(null);
    }

    selectFormUser(id: number | null) {
        this.borrowForm.patchValue({ userId: id });
        if (id) {
            const u = this.users().find((x) => x.id === id);
            this.formUserSearchQuery.set(u ? `${u.name} ${u.surname}` : '');
        } else {
            this.formUserSearchQuery.set('');
        }
        this.showFormUserDropdown.set(false);
    }

    selectFormBook(id: number | null) {
        this.borrowForm.patchValue({ bookCopyId: id });
        if (id) {
            const b = this.books().find((x) => x.id === id);
            this.formBookSearchQuery.set(b ? b.title : '');
        } else {
            this.formBookSearchQuery.set('');
        }
        this.showFormBookDropdown.set(false);
    }

    clearFormUser(event?: Event) {
        if (event) event.stopPropagation();
        this.selectFormUser(null);
    }

    clearFormBook(event?: Event) {
        if (event) event.stopPropagation();
        this.selectFormBook(null);
    }

    openCreate() {
        this.editorOpen.set(true);
        this.editingId.set(null);
        this.borrowForm.reset({ userId: null, bookCopyId: null, borrowedAt: new Date().toISOString(), dueDate: new Date().toISOString() });
        this.formUserSearchQuery.set('');
        this.formBookSearchQuery.set('');
    }

    openEdit(row: Borrowing) {
        this.editorOpen.set(true);
        this.editingId.set(row.id);
        this.borrowForm.patchValue({
            userId: row.userId,
            bookCopyId: row.bookCopyId,
            borrowedAt: row.borrowedAt,
            dueDate: row.dueDate,
        });
        const u = this.users().find((x) => x.id === row.userId);
        const b = this.books().find((x) => x.id === row.bookCopyId);
        this.formUserSearchQuery.set(u ? `${u.name} ${u.surname}` : '');
        this.formBookSearchQuery.set(b ? b.title : '');
    }

    save() {
        const editingId = this.editingId();
        const payload: BorrowingDto = {
            userId: Number(this.borrowForm.value.userId ?? 0),
            bookCopyId: Number(this.borrowForm.value.bookCopyId ?? 0),
            borrowedAt: this.borrowForm.value.borrowedAt ?? new Date().toISOString(),
            dueDate: this.borrowForm.value.dueDate ?? new Date().toISOString(),
        };

        if (!payload.userId || !payload.bookCopyId) {
            this.toast.show('Please select a user and a book', 3000, 'warning');
            return;
        }

        const request = editingId
            ? this.resourcesService.updateBorrowing(editingId, payload)
            : this.resourcesService.createBorrowing(payload);

        request.subscribe({
            next: () => {
                this.toast.show('Saved successfully');
                this.editorOpen.set(false);
                this.loadBorrowings();
            },
            error: () => this.toast.show('Save failed', 3000, 'danger'),
        });
    }

    delete(row: Borrowing) {
        const confirmed = window.confirm('Delete this borrowing?');
        if (!confirmed) return;
        this.resourcesService.deleteBorrowing(row.id).subscribe({
            next: () => {
                this.toast.show('Deleted');
                this.loadBorrowings();
            },
            error: () => this.toast.show('Delete failed', 3000, 'danger'),
        });
    }

    isOverdue(dateStr?: string) {
        if (!dateStr) return false;
        return new Date(dateStr) < new Date();
    }

    getUserById(userId: number) {
        return this.users().find((u) => u.id == userId) ?? null;
    }
}
