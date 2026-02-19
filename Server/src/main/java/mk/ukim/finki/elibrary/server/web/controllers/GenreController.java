package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.create.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayGenreDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateGenreDto;
import mk.ukim.finki.elibrary.server.service.backend.application.GenreApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreApplicationService genreApplicationService;

    public GenreController(GenreApplicationService genreApplicationService) {
        this.genreApplicationService = genreApplicationService;
    }

    @GetMapping
    public List<DisplayGenreDto> getAll() {
        return genreApplicationService.getAllGenres();
    }

    @GetMapping("/get/{id}")
    public DisplayGenreDto getById(@PathVariable Long id) {
        return genreApplicationService.getGenreById(id);
    }

    //@PreAuthorize("hasAuthority('LIBRARIAN')")
    @PostMapping("/create")
    public DisplayGenreDto addGenre(@RequestBody CreateGenreDto dto) {
        return genreApplicationService.createGenre(dto);
    }

    //   @PreAuthorize("hasAuthority('LIBRARIAN')")
    @PutMapping("update/{id}")
    public DisplayGenreDto updateGenre(@PathVariable Long id, @RequestBody UpdateGenreDto dto) {
        return genreApplicationService.updateGenre(id, dto);
    }

    //  @PreAuthorize("hasAuthority('LIBRARIAN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreApplicationService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
