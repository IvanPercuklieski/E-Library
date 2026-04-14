const fs = require('fs');
const filePath = 'src/app/features/admin-panel/components/admin-panel/admin-panel.component.html';
const content = fs.readFileSync(filePath, 'utf8');

const regex = /<ion-label position="stacked">Genres<\/ion-label>\s*<div class="faux-input-container">[\s\S]*?<ion-input\s+placeholder="Search genres\.\.\."[\s\S]*?class="flex-input"\s*><\/ion-input>\s*<\/div>/;

const replacement = `<ion-label position="stacked">Genres</ion-label>
                                                                <div style="width: 100%;">
                                                                        <ion-input 
                                                                                placeholder="Search genres..."
                                                                                [value]="genreSearchQuery()"
                                                                                (ionInput)="onGenreSearchChange($event)"
                                                                                (ionFocus)="showGenreDropdown.set(true)"
                                                                                (ionBlur)="hideGenreDropdown()"
                                                                                class="flex-input"
                                                                        ></ion-input>
                                                                        @if (selectedGenres().length > 0) {
                                                                                <div class="chips-container-under" style="margin-top: 8px; margin-bottom: 8px; display: flex; flex-wrap: wrap; gap: 8px;">
                                                                                        @for (genre of selectedGenres(); track genre.id) {
                                                                                                <ion-chip class="custom-chip">
                                                                                                        <ion-label>{{ genre.name }}</ion-label>
                                                                                                        <ion-icon name="close-circle" (click)="$event.stopPropagation(); toggleGenre(genre.id)"></ion-icon>
                                                                                                </ion-chip>
                                                                                        }
                                                                                </div>
                                                                        }
                                                                </div>`;

if(content.match(regex)) {
    fs.writeFileSync(filePath, content.replace(regex, replacement), 'utf8');
    console.log('Successfully replaced!');
} else {
    console.log('Regex did not match!');
}const fs = require('fs');
const path = 'src/app/features/admin-panel/components/admin-panel/admin-panel.component.html';
let content = fs.readFileSync(path, 'utf8');

const regex = /<ion-label position=\"stacked\">Genres<\/ion-label>\s*<div class=\"faux-input-container\">\s*@if \(selectedGenres\(\)\.length > 0\) \{([\s\S]*?)<ion-input[\s\S]*?class=\"flex-input\"\s*><\/ion-input>\s*<\/div>/g;

content = content.replace(regex, \<ion-label position="stacked">Genres</ion-label>
                                <div style="width: 100%;">
                                    <ion-input 
                                        placeholder="Search genres..."
                                        [value]="genreSearchQuery()"
                                        (ionInput)="onGenreSearchChange(\\)"
                                        (ionFocus)="showGenreDropdown.set(true)"
                                        (ionBlur)="hideGenreDropdown()"
                                        class="flex-input"
                                    ></ion-input>
                                
                                    @if (selectedGenres().length > 0) {
                                        <div class="chips-container-under" style="margin-top: 8px; margin-bottom: 8px; display: flex; flex-wrap: wrap; gap: 8px;">
                                            @for (genre of selectedGenres(); track genre.id) {
                                                <ion-chip class="custom-chip">
                                                    <ion-label>{{ genre.name }}</ion-label>
                                                    <ion-icon name="close-circle" (click)="\\.stopPropagation(); toggleGenre(genre.id)"></ion-icon>
                                                </ion-chip>
                                            }
                                        </div>
                                    }
                                </div>\);

fs.writeFileSync(path, content, 'utf8');
console.log('Done');
