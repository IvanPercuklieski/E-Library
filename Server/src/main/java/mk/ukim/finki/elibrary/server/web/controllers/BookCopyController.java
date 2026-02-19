package mk.ukim.finki.elibrary.server.web.controllers;


import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookCopyDto;
import mk.ukim.finki.elibrary.server.service.backend.application.BookCopyApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-copies")
public class BookCopyController {

    private final BookCopyApplicationService bookCopyApplicationService;

    public BookCopyController(BookCopyApplicationService bookCopyApplicationService) {
        this.bookCopyApplicationService = bookCopyApplicationService;
    }

    //   @PreAuthorize("hasAuthority('LIBRARIAN')")
    @PostMapping("/create")
    public ResponseEntity<DisplayBookCopyDto> create(@RequestBody CreateBookCopyDto dto) {
        DisplayBookCopyDto created = bookCopyApplicationService.createBookCopy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<List<DisplayBookCopyDto>> getAll() {
        return ResponseEntity.ok(bookCopyApplicationService.getAllBookCopies());
    }

    @GetMapping("/by-book/{bookId}")
    public ResponseEntity<List<DisplayBookCopyDto>> getAllByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookCopyApplicationService.getAllBookCopiesByBook(bookId));
    }


    @GetMapping("/get/{bookCopyId}")
    public ResponseEntity<DisplayBookCopyDto> getById(@PathVariable Long bookCopyId) {
        return ResponseEntity.ok(bookCopyApplicationService.getBookCopyById(bookCopyId));
    }


    //   @PreAuthorize("hasAuthority('LIBRARIAN')")
    @DeleteMapping("/delete/{bookCopyId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookCopyId) {
        bookCopyApplicationService.deleteBookCopy(bookCopyId);
        return ResponseEntity.noContent().build();
    }


    //   @PreAuthorize("hasAuthority('LIBRARIAN')")
    @DeleteMapping("/delete-by-book/{bookId}")
    public ResponseEntity<Void> deleteAllByBook(@PathVariable Long bookId) {
        bookCopyApplicationService.deleteAllByBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
