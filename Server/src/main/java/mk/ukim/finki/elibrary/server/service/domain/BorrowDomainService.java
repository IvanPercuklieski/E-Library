package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;

import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;


import java.time.LocalDate;
import java.util.List;


public interface BorrowDomainService {

    BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDate borrowDate, LocalDate dueDate);

    void returnBook(Long borrowedBookId, LocalDate returnDate);

    List<BorrowedBook> getBorrowedBooksByUser(Long userId);

    List<BookBorrowLog> getBorrowHistory(Long bookCopyId);

    double calculateRentalFee(Long userId, int days);

    boolean isBookAvailable(Long bookCopyId);
}
