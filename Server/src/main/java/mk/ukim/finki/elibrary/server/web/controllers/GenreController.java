package mk.ukim.finki.elibrary.server.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.elibrary.server.dto.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.DisplayGenreDto;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreAlreadyExistsException;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreNameNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreNotFoundException;
import mk.ukim.finki.elibrary.server.service.backend.application.GenreApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@Tag(name = "Genre API", description = "Endpoints for managing genres.")
public class GenreController {

    private final GenreApplicationService genreApplicationService;


    public GenreController(GenreApplicationService genreApplicationService) {
        this.genreApplicationService = genreApplicationService;
    }

    @GetMapping("/all")
    @Operation(summary = "List all genres", description = "Retrieves all of the available genres in the library.\n**PERMISSIONS: EVERYONE)**")
    public List<DisplayGenreDto> getAllGenres() {
        return genreApplicationService.getAllGenres();
    }

    @PostMapping("/add")
    @Operation(summary = "Add a genre", description = "Employees can add new genres.\n **PERMISSIONS: EMPLOYEES (ADMIN)**")
    public ResponseEntity<?> addGenre(@RequestBody CreateGenreDto genreDto) {
        try{
            return ResponseEntity.ok(genreApplicationService.addGenre(genreDto));
        }catch (GenreAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{genreId}")
    @Operation(summary = "Delete a genre by id", description = "Employees can delete genre by their id provided as PATH VARIABLE.\n **PERMISSIONS: EMPLOYEES (ADMIN)**")
    public ResponseEntity<?> deleteGenreById(@PathVariable Long genreId) {
        try{
            return ResponseEntity.ok(genreApplicationService.deleteGenreById(genreId));
        }catch (GenreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-name")
    @Operation(summary = "Delete a genre by name", description = "Employees can delete genre by their name provided as REQUEST PARAMETER.\n **PERMISSIONS: EMPLOYEES (ADMIN)**")
    public ResponseEntity<?> deleteGenreByName(@RequestParam String genreName) {
        try{
            return ResponseEntity.ok(genreApplicationService.deleteGenreByName(genreName));
        }catch (GenreNameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
