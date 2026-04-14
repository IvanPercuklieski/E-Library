import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { addIcons } from 'ionicons';
import { createOutline, trashOutline, checkmarkOutline, closeOutline, closeCircleOutline, closeCircle } from 'ionicons/icons';
import { Observable } from 'rxjs';
import { catchError, of } from 'rxjs';
import { Author, Book, Employee, EmployeeRegisterDto, Genre, LibraryUser } from 'src/app/core/models/modeli';
import { Auth } from 'src/app/features/auth/services/auth';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { ToastService } from 'src/app/shared/services/toast';
import { ResourceManagerService } from '../../services/admin-panel';

type ResourceType = 'book' | 'genre' | 'author' | 'user' | 'employee';

interface ResourceOption {
	value: ResourceType;
	label: string;
}

interface ResourceColumn {
	key: string;
	label: string;
}

@Component({
	selector: 'app-admin-panel',
	templateUrl: './admin-panel.component.html',
	styleUrls: ['./admin-panel.component.scss'],
	imports: [CommonModule, ReactiveFormsModule, IonicModule, HeaderComponent],
})
export class AdminPanelComponent implements OnInit {
	private resourcesService = inject(ResourceManagerService);
	private toastService = inject(ToastService);
	private authService = inject(Auth);

	constructor() {
		addIcons({ createOutline, trashOutline, checkmarkOutline, closeOutline, closeCircleOutline, closeCircle });
	}

	resourceOptions: ResourceOption[] = [
		{ value: 'book', label: 'Books' },
		{ value: 'genre', label: 'Genres' },
		{ value: 'author', label: 'Authors' },
		{ value: 'user', label: 'Users' },
	];

	selectedResource = signal<ResourceType>('book');
	rows = signal<Array<Book | Genre | Author | LibraryUser | Employee>>([]);
	authors = signal<Author[]>([]);
	genres = signal<Genre[]>([]);
	users = signal<LibraryUser[]>([]);

	isLoading = signal(false);
	editorOpen = signal(false);
	editingId = signal<number | null>(null);

	searchQuery = signal('');
	authorSearchQuery = signal('');
	genreSearchQuery = signal('');

	showAuthorDropdown = signal(false);
	showGenreDropdown = signal(false);

	filteredRows = computed(() => {
		const query = this.searchQuery().trim().toLowerCase();
		const allRows = this.rows();
		if (!query) return allRows;

		const columns = this.getColumns();

		return allRows.filter((row: any) => {
			return columns.some(col => {
				const cellValue = this.getCellValue(row, col.key);
				return String(cellValue).toLowerCase().includes(query);
			});
		});
	});

	filteredAuthors = computed(() => {
		const query = this.authorSearchQuery().trim().toLowerCase();
		if (!query) return this.authors().slice(0, 50);
		return this.authors().filter((a) => a.name.toLowerCase().includes(query)).slice(0, 50);
	});

	filteredGenres = computed(() => {
		const query = this.genreSearchQuery().trim().toLowerCase();
		if (!query) return this.genres().slice(0, 50);
		return this.genres().filter((g) => g.name.toLowerCase().includes(query)).slice(0, 50);
	});

	selectedGenres() {
		const ids = this.resourceForm.value.genreIds || [];
		if (ids.length === 0) return [];
		return this.genres().filter((g) => ids.includes(g.id));
	}

	selectedAuthorName() {
		const id = this.resourceForm.value.authorId;
		if (!id) return 'Select Author';
		return this.authors().find((a) => a.id === id)?.name || 'Select Author';
	}

	resourceForm = new FormGroup({
		title: new FormControl('', Validators.required),
		authorId: new FormControl<number | null>(null),
		genreIds: new FormControl<number[]>([]),
		pubDate: new FormControl(''),
		description: new FormControl(''),
		numBooks: new FormControl<number>(0),
		name: new FormControl(''),
		surname: new FormControl(''),
		email: new FormControl(''),
		userId: new FormControl<number | null>(null),
		username: new FormControl(''),
		password: new FormControl(''),
		repeatPassword: new FormControl(''),
	});

	ngOnInit(): void {
		if (this.authService.hasAnyRole(['ADMIN'])) {
			this.resourceOptions.push({ value: 'employee', label: 'Employees' });
		}
		this.loadReferenceData();
		this.loadCurrentResource();
	}

	onResourceChange(event: CustomEvent) {
		this.selectedResource.set(event.detail.value as ResourceType);
		this.editorOpen.set(false);
		this.editingId.set(null);
		this.searchQuery.set('');
		this.resetForm();
		this.loadCurrentResource();
	}

	openCreate() {
		this.editorOpen.set(true);
		this.editingId.set(null);
		this.showAuthorDropdown.set(false);
		this.showGenreDropdown.set(false);
		this.authorSearchQuery.set('');
		this.genreSearchQuery.set('');
		this.resetForm();
	}

	closeEditor() {
		this.editorOpen.set(false);
		this.editingId.set(null);
		this.showAuthorDropdown.set(false);
		this.showGenreDropdown.set(false);
	}

	onSearchChange(event: CustomEvent) {
		this.searchQuery.set(event.detail.value ?? '');
	}

	onAuthorSearchChange(event: CustomEvent) {
		this.authorSearchQuery.set(event.detail.value ?? '');
		this.showAuthorDropdown.set(true);
	}

	onGenreSearchChange(event: CustomEvent) {
		this.genreSearchQuery.set(event.detail.value ?? '');
		this.showGenreDropdown.set(true);
	}

	hideAuthorDropdown() {
		setTimeout(() => this.showAuthorDropdown.set(false), 200);
	}

	hideGenreDropdown() {
		setTimeout(() => this.showGenreDropdown.set(false), 200);
	}

	clearAuthor(event?: Event) {
		if (event) {
			event.stopPropagation();
		}
		this.resourceForm.patchValue({ authorId: null });
		this.authorSearchQuery.set('');
	}

	selectAuthor(id: number) {
		this.resourceForm.patchValue({ authorId: id });
		this.authorSearchQuery.set(this.authors().find((a) => a.id === id)?.name || '');
		this.showAuthorDropdown.set(false);
	}

	toggleGenre(id: number) {
		const current = this.resourceForm.value.genreIds || [];
		if (current.includes(id)) {
			this.resourceForm.patchValue({ genreIds: current.filter((g) => g !== id) });
		} else {
			this.resourceForm.patchValue({ genreIds: [...current, id] });
		}
	}

	openEdit(row: Book | Genre | Author | LibraryUser | Employee) {
		this.editorOpen.set(true);
		this.editingId.set((row as any).id);
		this.showAuthorDropdown.set(false);
		this.showGenreDropdown.set(false);
		const resource = this.selectedResource();

		if (resource === 'book') {
			const book = row as Book;
			this.resourceForm.patchValue({
				title: book.title,
				authorId: book.authorId ?? null,
				genreIds: book.genreIds ?? [],
				pubDate: book.pubDate,
				description: book.description,
				numBooks: book.numBooks ?? book.totalBookCopies,
			});
			this.authorSearchQuery.set(this.selectedAuthorName());
			this.genreSearchQuery.set('');
			return;
		}

		if (resource === 'genre') {
			this.resourceForm.patchValue({
				title: (row as Genre).name,
			});
			return;
		}

		if (resource === 'author') {
			this.resourceForm.patchValue({
				name: (row as Author).name,
			});
			return;
		}

		if (resource === 'employee') {
			const emp = row as Employee;
			const linkedUser = this.getUserById(emp.userId);
			this.resourceForm.patchValue({
				username: emp.username,
				email: linkedUser?.email ?? emp.email ?? '',
				userId: emp.userId
			});
			return;
		}

		this.resourceForm.patchValue({
			name: (row as LibraryUser).name,
			surname: (row as LibraryUser).surname,
			email: (row as LibraryUser).email,
		});
	}

	save() {
		const resource = this.selectedResource();
		const editingId = this.editingId();

		if (resource === 'book') {
			const payload = {
				title: this.resourceForm.value.title ?? '',
				authorId: Number(this.resourceForm.value.authorId ?? 0),
				genreIds: this.resourceForm.value.genreIds ?? [],
				pubDate: this.resourceForm.value.pubDate ?? '',
				description: this.resourceForm.value.description ?? '',
				numBooks: Number(this.resourceForm.value.numBooks ?? 0),
			};

			if (!payload.title || !payload.authorId || payload.genreIds.length === 0 || !payload.pubDate) {
				this.toastService.show('Fill all required Book fields', 3000, 'warning');
				return;
			}

			const request = editingId
				? this.resourcesService.updateBook(editingId, payload)
				: this.resourcesService.createBook(payload);

			request.subscribe({
				next: () => this.handleSaveSuccess('Book saved successfully'),
				error: () => this.toastService.show('Book save failed', 3000, 'danger'),
			});
			return;
		}

		if (resource === 'genre') {
			const payload = { name: this.resourceForm.value.title ?? '' };
			if (!payload.name.trim()) {
				this.toastService.show('Genre name is required', 3000, 'warning');
				return;
			}

			const request = editingId
				? this.resourcesService.updateGenre(editingId, payload)
				: this.resourcesService.createGenre(payload);

			request.subscribe({
				next: () => this.handleSaveSuccess('Genre saved successfully'),
				error: () => this.toastService.show('Genre save failed', 3000, 'danger'),
			});
			return;
		}

		if (resource === 'employee') {
			const selectedUser = this.getUserById(Number(this.resourceForm.value.userId ?? 0));
			const payload: EmployeeRegisterDto = {
				userId: Number(this.resourceForm.value.userId),
				username: this.resourceForm.value.username ?? '',
				password: this.resourceForm.value.password ?? '',
				repeatPassword: this.resourceForm.value.repeatPassword ?? '',
				email: selectedUser?.email ?? this.resourceForm.value.email ?? ''
			};
			if (!payload.userId || !payload.username || !payload.password || !payload.repeatPassword) {
				this.toastService.show('Fill all required Employee fields', 3000, 'warning');
				return;
			}

			if (!payload.email) {
				this.toastService.show('Select a user with an email', 3000, 'warning');
				return;
			}

			if (payload.password !== payload.repeatPassword) {
				this.toastService.show('Passwords do not match', 3000, 'warning');
				return;
			}

			const request = this.resourcesService.createEmployee(payload);
			request.subscribe({
				next: () => this.handleSaveSuccess('Employee registered successfully'),
				error: () => this.toastService.show('Employee registration failed', 3000, 'danger'),
			});
			return;
		}

		if (resource === 'author') {
			const payload = { name: this.resourceForm.value.name ?? '' };
			if (!payload.name.trim()) {
				this.toastService.show('Author name is required', 3000, 'warning');
				return;
			}

			const request = editingId
				? this.resourcesService.updateAuthor(editingId, payload)
				: this.resourcesService.createAuthor(payload);

			request.subscribe({
				next: () => this.handleSaveSuccess('Author saved successfully'),
				error: () => this.toastService.show('Author save failed', 3000, 'danger'),
			});
			return;
		}

		const payload = {
			name: this.resourceForm.value.name ?? '',
			surname: this.resourceForm.value.surname ?? '',
			email: this.resourceForm.value.email ?? '',
		};

		if (!payload.name.trim() || !payload.surname.trim() || !payload.email.trim()) {
			this.toastService.show('All user fields are required', 3000, 'warning');
			return;
		}

		const request = editingId
			? this.resourcesService.updateUser(editingId, payload)
			: this.resourcesService.createUser(payload);

		request.subscribe({
			next: () => this.handleSaveSuccess('User saved successfully'),
			error: () => this.toastService.show('User save failed', 3000, 'danger'),
		});
	}

	deleteRow(row: Book | Genre | Author | LibraryUser | Employee) {
		const rowId = (row as any).id;
		if (!rowId) {
			return;
		}

		const resource = this.selectedResource();
		if (resource === 'employee') {
			this.toastService.show('Cannot delete employees', 3000, 'warning');
			return;
		}

		const confirmed = window.confirm('Are you sure you want to delete this record?');
		if (!confirmed) {
			return;
		}

		let request;
		if (resource === 'book') {
			request = this.resourcesService.deleteBook(rowId);
		} else if (resource === 'genre') {
			request = this.resourcesService.deleteGenre(rowId);
		} else if (resource === 'author') {
			request = this.resourcesService.deleteAuthor(rowId);
		} else {
			request = this.resourcesService.deleteUser(rowId);
		}

		request.subscribe({
			next: () => {
				this.toastService.show('Record deleted successfully');
				this.loadCurrentResource();
			},
			error: () => this.toastService.show('Delete failed', 3000, 'danger'),
		});
	}

	getColumns(): ResourceColumn[] {
		if (this.selectedResource() === 'book') {
			return [
				{ key: 'title', label: 'Title' },
				{ key: 'authorName', label: 'Author' },
				{ key: 'genreNames', label: 'Genres' },
				{ key: 'pubDate', label: 'Publication Date' },
				{ key: 'totalBookCopies', label: 'Copies' },
			];
		}

		if (this.selectedResource() === 'genre') {
			return [{ key: 'name', label: 'Name' }];
		}

		if (this.selectedResource() === 'author') {
			return [{ key: 'name', label: 'Name' }];
		}

		if (this.selectedResource() === 'employee') {
			return [
				{ key: 'username', label: 'Username' },
				{ key: 'email', label: 'Email' },
				{ key: 'role', label: 'Role' },
			];
		}

		return [
			{ key: 'name', label: 'Name' },
			{ key: 'surname', label: 'Surname' },
			{ key: 'email', label: 'Email' },
		];
	}

	getCellValue(row: any, key: string): string | number {
		if (this.selectedResource() === 'employee') {
			if (key === 'email') {
				return this.getUserById(Number(row.userId))?.email ?? row.email ?? '-';
			}

			if (key === 'role') {
				return row.role ?? '-';
			}
		}

		if (key === 'genreNames') {
			return (row.genreNames ?? []).join(', ');
		}

		if (key === 'name') {
			return row.name ?? row.Name ?? row.title ?? '-';
		}

		return row[key] ?? '-';
	}

	onEmployeeUserChange(event: CustomEvent) {
		const userId = Number(event.detail.value ?? 0);
		const user = this.getUserById(userId);
		this.resourceForm.patchValue({
			userId: userId || null,
			email: user?.email ?? '',
		});
	}

	onGenreSelectChange(event: CustomEvent) {
		const values = (event.detail.value ?? []) as Array<number | string>;
		this.resourceForm.patchValue({
			genreIds: values.map((value) => Number(value)),
		});
	}

	private handleSaveSuccess(message: string) {
		this.toastService.show(message);
		this.editorOpen.set(false);
		this.editingId.set(null);
		this.resetForm();
		this.loadCurrentResource();
	}

	private loadReferenceData() {
		this.resourcesService
			.getAuthors()
			.pipe(catchError(() => of([])))
			.subscribe((authors) => this.authors.set(authors));

		this.resourcesService
			.getGenres()
			.pipe(catchError(() => of([])))
			.subscribe((genres) => this.genres.set(genres));
		
		this.resourcesService
			.getUsers()
			.pipe(catchError(() => of([])))
			.subscribe((users) => this.users.set(users));
	}

	private loadCurrentResource() {
		this.isLoading.set(true);
		let request: Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;

		if (this.selectedResource() === 'book') {
			request = this.resourcesService.getBooks() as Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;
		} else if (this.selectedResource() === 'genre') {
			request = this.resourcesService.getGenres() as Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;
		} else if (this.selectedResource() === 'author') {
			request = this.resourcesService.getAuthors() as Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;
		} else if (this.selectedResource() === 'employee') {
			request = this.resourcesService.getEmployees() as Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;
		} else {
			request = this.resourcesService.getUsers() as Observable<Array<Book | Genre | Author | LibraryUser | Employee>>;
		}

		request.pipe(catchError(() => of([]))).subscribe((rows) => {
			this.rows.set(rows);
			this.isLoading.set(false);
		});
	}

	private getUserById(userId: number) {
		return this.users().find((user) => user.id === userId);
	}

	private resetForm() {
		this.resourceForm.reset({
			title: '',
			authorId: null,
			genreIds: [],
			pubDate: '',
			description: '',
			numBooks: 0,
			name: '',
			surname: '',
			email: '',
			userId: null,
			username: '',
			password: '',
			repeatPassword: '',
		});
	}
}
