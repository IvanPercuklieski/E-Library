package mk.ukim.finki.elibrary.server.web.controllers;


import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;

import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;

import mk.ukim.finki.elibrary.server.service.backend.application.BorrowApplicationService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
public class BookBorrowController {

    private final BorrowApplicationService service;


    public BookBorrowController(BorrowApplicationService service) {
        this.service = service;
    }


    @PostMapping("/borrow")
    public BorrowedBook borrow(@RequestBody CreateBorrowedBookDto dto) {
        return service.borrowBook(dto.userId(), dto.bookCopyId(), dto.borrowedAt().toLocalDate(), dto.dueDate().toLocalDate());
    }


    @GetMapping
    public List<BorrowedBook> all() {
        return service.listAll();
    }


    @GetMapping("/user/{userId}")
    public List<BorrowedBook> getByUser(@PathVariable Long userId) {
        return service.getBorrowedBooksByUser(userId);
    }


    @PostMapping("/return/{borrowedBookId}")
    public void returnBook(@PathVariable Long borrowedBookId, @RequestBody Map<String, String> body) {
        LocalDate returnDate = LocalDate.parse(body.get("returnDate"));
        service.returnBook(borrowedBookId, returnDate);
    }

    @GetMapping("/price")
    public double calculatePrice(@RequestParam Long userId) {
        return service.calculateRentalFee(userId);
    }

}
