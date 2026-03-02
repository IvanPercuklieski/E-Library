import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})

export class StorageService {
    set<T>(key: string, value: T) {
        localStorage.setItem(key, JSON.stringify(value));
    }

    get<T>(key: string): T | null {
        const item = localStorage.getItem(key);
        return item ? JSON.parse(item) as T : null;
    }

    remove(key: string) {
        localStorage.removeItem(key);
    }

    clear() {
        localStorage.clear();
    }
}