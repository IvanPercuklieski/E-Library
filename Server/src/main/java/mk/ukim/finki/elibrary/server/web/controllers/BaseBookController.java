package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.*;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBaseBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBaseBookDto;
import mk.ukim.finki.elibrary.server.service.backend.application.BookApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BaseBookController {

    private final BookApplicationService booksApplicationService;

    public BaseBookController(BookApplicationService booksApplicationService) {
        this.booksApplicationService = booksApplicationService;
    }


    @GetMapping
    public List<DisplayBaseBookDto> getAll() {
        return booksApplicationService.getAllBooks();
    }

    @GetMapping("/search")
    public List<DisplayBaseBookDto> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) List<Long> genreIds
    ) {
        return booksApplicationService.searchBooks(title, authorId, genreIds);
    }


    @GetMapping("/get/{id}")
    public DisplayBaseBookDto getById(@PathVariable Long id) {
        return booksApplicationService.getBookById(id);
    }

  //@PreAuthorize("hasAuthority('LIBRARIAN')")
    @PostMapping("/create")
    public DisplayBookBaseDto addBook(@RequestBody CreateBaseBookDto dto) {
        return booksApplicationService.createBook(dto);
    }

//   @PreAuthorize("hasAuthority('LIBRARIAN')")
    @PutMapping("update/{id}")
    public DisplayBaseBookDto updateBook(@PathVariable Long id, @RequestBody UpdateBaseBookDto dto) {
        return booksApplicationService.updateBook(id, dto);
    }

//  @PreAuthorize("hasAuthority('LIBRARIAN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksApplicationService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
