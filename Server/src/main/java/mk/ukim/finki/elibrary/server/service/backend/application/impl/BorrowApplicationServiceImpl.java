package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.service.backend.application.BorrowApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BorrowDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BorrowApplicationServiceImpl implements BorrowApplicationService {

    private final BorrowDomainService borrowDomainService;

    public BorrowApplicationServiceImpl(BorrowDomainService borrowDomainService) {
        this.borrowDomainService = borrowDomainService;
    }

    @Override
    public BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDate borrowDate, LocalDate dueDate) {
        return borrowDomainService.borrowBook(userId, bookCopyId, borrowDate, dueDate);
    }

    @Override
    public void returnBook(Long borrowedBookId, LocalDate returnDate) {
        borrowDomainService.returnBook(borrowedBookId, returnDate);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUser(Long userId) {
        return borrowDomainService.getBorrowedBooksByUser(userId);
    }

    @Override
    public List<BookBorrowLog> getBorrowHistory(Long bookCopyId) {
        return borrowDomainService.getBorrowHistory(bookCopyId);
    }

    @Override
    public double calculateRentalFee(Long userId) {
        return borrowDomainService.calculateRentalFee(userId);
    }

    @Override
    public boolean isBookAvailable(Long bookCopyId) {
        return borrowDomainService.isBookAvailable(bookCopyId);
    }

    @Override
    public List<BorrowedBook> listAll() {
        return borrowDomainService.allBorrowedBooks();
    }
}
