package mk.ukim.finki.elibrary.server.web.controllers;


import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;

import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;

import mk.ukim.finki.elibrary.server.service.backend.application.BorrowedBookApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookDomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowings")
public class BookBorrowController {

    private final BorrowedBookDomainService borrowedBookDomainService;

    public BookBorrowController(BorrowedBookDomainService borrowedBookDomainService) {
        this.borrowedBookDomainService = borrowedBookDomainService;
    }


    @PostMapping("/create")
    public ResponseEntity<DisplayBorrowedBookDto> create(@RequestBody CreateBorrowedBookDto dto) {
        BorrowedBook created = borrowedBookDomainService.createBookBorrowing(dto);
        return ResponseEntity.ok(DisplayBorrowedBookDto.from(created));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<DisplayBorrowedBookDto> update(@PathVariable Long id,
                                                         @RequestBody UpdateBorrowedBookDto dto) {
        BorrowedBook updated = borrowedBookDomainService.updateBookBorrowing(id, dto);
        return ResponseEntity.ok(DisplayBorrowedBookDto.from(updated));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWithLog(@PathVariable Long id) {
        borrowedBookDomainService.deleteBookBorrowing(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<DisplayBorrowedBookDto>> getAll() {
        List<DisplayBorrowedBookDto> result = borrowedBookDomainService.getAllBookBorrowings()
                .stream()
                .map(DisplayBorrowedBookDto::from)
                .toList();
        return ResponseEntity.ok(result);
    }


    @GetMapping("/get/{borrowingId}")
    public ResponseEntity<DisplayBorrowedBookDto> getById(@PathVariable Long borrowingId) {
        BorrowedBook borrowing = borrowedBookDomainService.getById(borrowingId);
        return ResponseEntity.ok(DisplayBorrowedBookDto.from(borrowing));
    }


    @GetMapping("/get/by-book/{bookId}")
    public ResponseEntity<List<DisplayBorrowedBookDto>> getByBook(@PathVariable Long bookId) {
        List<DisplayBorrowedBookDto> result = borrowedBookDomainService.getAllBookBorrowingsByBook(bookId)
                .stream()
                .map(DisplayBorrowedBookDto::from)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/by-user/{userId}")
    public ResponseEntity<List<DisplayBorrowedBookDto>> getByUser(@PathVariable Long userId) {
        List<DisplayBorrowedBookDto> result = borrowedBookDomainService.getAllBookBorrowingsByUser(userId)
                .stream()
                .map(DisplayBorrowedBookDto::from)
                .toList();
        return ResponseEntity.ok(result);
    }
}
