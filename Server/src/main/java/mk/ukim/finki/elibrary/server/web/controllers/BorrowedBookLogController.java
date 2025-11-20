package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;

import mk.ukim.finki.elibrary.server.service.backend.application.impl.BorrowedBookLogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

//Ovoj kontroler za create-metoda na log nema da vi treba(beshe za testiranje), bidejki treba da se
//kreira objektot koga kje se kreira bookborrowing, samo logot kje bide returedAt -> null
//(moeto mislenje e da nema specijalen endpoint za create log,
// no da si ostanat ostanatite endpointi za drugite operacii pr. /end kade shto kje pishuvate
// notes i returnedAt)


@RestController
@RequestMapping("/api/borrow-logs")
public class BorrowedBookLogController {

    private final BorrowedBookLogServiceImpl bookBorrowLogService;
    private final UserWrapperRepository userWrapperRepository;
    private final BookCopyRepository bookCopyRepository;
    private final Random random = new Random();

    public BorrowedBookLogController(BorrowedBookLogServiceImpl bookBorrowLogService,
                                     UserWrapperRepository userWrapperRepository,
                                     BookCopyRepository bookCopyRepository) {
        this.bookBorrowLogService = bookBorrowLogService;
        this.userWrapperRepository = userWrapperRepository;
        this.bookCopyRepository = bookCopyRepository;
    }


    @PostMapping
    public ResponseEntity<BookBorrowLog> createBorrowLog(
            @RequestBody CreateBookBorrowLogDto request) {

        BookBorrowLog log = bookBorrowLogService.createBorrowLog(
                request.userId(),
                request.bookCopyId(),
                request.borrowedAt(),
                request.dueDate(),
                request.notes()
        );

        return ResponseEntity.status(201).body(log);
    }


    @GetMapping("/simulate")
    @PostMapping("/simulate")
    public ResponseEntity<BookBorrowLog> simulateRandomBorrow() {

        List<UserWrapper> users = userWrapperRepository.findAll();
        List<BookCopy> copies = bookCopyRepository.findAll();

        if (users.isEmpty() || copies.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserWrapper user = users.get(random.nextInt(users.size()));
        BookCopy copy = copies.get(random.nextInt(copies.size()));

        BookBorrowLog log = bookBorrowLogService.createBorrowLog(
                Long.parseLong("16"),
                copy.getId(),
                LocalDateTime.of(2025, 1, 10, 14, 30),
                LocalDateTime.of(2025, 1, 20, 14, 30),
                "Simulated borrow for testing ML"
        );

        return ResponseEntity.status(201).body(log);
    }
}
