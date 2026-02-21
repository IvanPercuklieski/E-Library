package mk.ukim.finki.elibrary.server.web.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.service.backend.application.BorrowedBookLogApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-logs")
public class BorrowedBookLogController {

    private final BorrowedBookLogApplicationService borrowedBookLogApplicationService;

    public BorrowedBookLogController(BorrowedBookLogApplicationService borrowedBookLogApplicationService) {
        this.borrowedBookLogApplicationService = borrowedBookLogApplicationService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<DisplayBookBorrowLogDto>> getAll() {
        return ResponseEntity.ok(borrowedBookLogApplicationService.getAll());
    }


    @GetMapping("/get/by-user/{userId}")
    public ResponseEntity<List<DisplayBookBorrowLogDto>> getAllForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowedBookLogApplicationService.getAllForUser(userId));
    }


    @GetMapping("/get/by-copy/{bookCopyId}")
    public ResponseEntity<List<DisplayBookBorrowLogDto>> getAllForBookCopy(@PathVariable Long bookCopyId) {
        return ResponseEntity.ok(borrowedBookLogApplicationService.getAllForBookCopy(bookCopyId));
    }


    @DeleteMapping("/delete/by-copy/{bookCopyId}")
    public ResponseEntity<Void> deleteAllForBookCopy(@PathVariable Long bookCopyId) {
        borrowedBookLogApplicationService.deleteAllForBookCopy(bookCopyId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/by-user/{userId}")
    public ResponseEntity<Void> deleteAllForUser(@PathVariable Long userId) {
        borrowedBookLogApplicationService.deleteAllForUser(userId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        borrowedBookLogApplicationService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}