import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { addIcons } from 'ionicons';
import { createOutline, trashOutline } from 'ionicons/icons';
import { Observable } from 'rxjs';
import { catchError, of } from 'rxjs';
import { Author, Book, Genre, LibraryUser } from 'src/app/core/models/modeli';
import { HeaderComponent } from 'src/app/shared/components/header/header.component';
import { ToastService } from 'src/app/shared/services/toast';
import { ResourceManagerService } from '../../services/admin-panel';

type ResourceType = 'book' | 'genre' | 'author' | 'user';

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

	constructor() {
		addIcons({ createOutline, trashOutline });
	}

	resourceOptions: ResourceOption[] = [
		{ value: 'book', label: 'Books' },
		{ value: 'genre', label: 'Genres' },
		{ value: 'author', label: 'Authors' },
		{ value: 'user', label: 'Users' },
	];

	selectedResource = signal<ResourceType>('book');
	rows = signal<Array<Book | Genre | Author | LibraryUser>>([]);
	authors = signal<Author[]>([]);
	genres = signal<Genre[]>([]);
	isLoading = signal(false);
	editorOpen = signal(false);
	editingId = signal<number | null>(null);

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
	});

	ngOnInit(): void {
		this.loadReferenceData();
		this.loadCurrentResource();
	}

	onResourceChange(event: CustomEvent) {
		this.selectedResource.set(event.detail.value as ResourceType);
		this.editorOpen.set(false);
		this.editingId.set(null);
		this.resetForm();
		this.loadCurrentResource();
	}

	openCreate() {
		this.editorOpen.set(true);
		this.editingId.set(null);
		this.resetForm();
	}

	closeEditor() {
		this.editorOpen.set(false);
		this.editingId.set(null);
	}

	openEdit(row: Book | Genre | Author | LibraryUser) {
		this.editorOpen.set(true);
		this.editingId.set((row as any).id);
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

	deleteRow(row: Book | Genre | Author | LibraryUser) {
		const rowId = (row as any).id;
		if (!rowId) {
			return;
		}

		const resource = this.selectedResource();
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

		return [
			{ key: 'name', label: 'Name' },
			{ key: 'surname', label: 'Surname' },
			{ key: 'email', label: 'Email' },
		];
	}

	getCellValue(row: any, key: string): string | number {
		if (key === 'genreNames') {
			return (row.genreNames ?? []).join(', ');
		}

		if (key === 'name') {
			return row.name ?? row.Name ?? row.title ?? '-';
		}

		return row[key] ?? '-';
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
	}

	private loadCurrentResource() {
		this.isLoading.set(true);
		let request: Observable<Array<Book | Genre | Author | LibraryUser>>;

		if (this.selectedResource() === 'book') {
			request = this.resourcesService.getBooks() as Observable<Array<Book | Genre | Author | LibraryUser>>;
		} else if (this.selectedResource() === 'genre') {
			request = this.resourcesService.getGenres() as Observable<Array<Book | Genre | Author | LibraryUser>>;
		} else if (this.selectedResource() === 'author') {
			request = this.resourcesService.getAuthors() as Observable<Array<Book | Genre | Author | LibraryUser>>;
		} else {
			request = this.resourcesService.getUsers() as Observable<Array<Book | Genre | Author | LibraryUser>>;
		}

		request.pipe(catchError(() => of([]))).subscribe((rows) => {
			this.rows.set(rows);
			this.isLoading.set(false);
		});
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
		});
	}
}
