package mk.ukim.finki.elibrary.server.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.exceptions.BookCopyNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.BorrowedBookNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.UserWrapperNotFoundException;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.BorrowDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional

public class BorrowDomainServiceImpl implements BorrowDomainService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BorrowedBookLogRepository borrowLogRepository;
    private final UserWrapperRepository userRepository;

    public BorrowDomainServiceImpl(BorrowedBookRepository borrowedBookRepository,
                                   BookCopyRepository bookCopyRepository,
                                   BorrowedBookLogRepository borrowLogRepository,
                                   UserWrapperRepository userRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.borrowLogRepository = borrowLogRepository;
        this.userRepository = userRepository;
    }


    public BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDateTime borrowDate, LocalDateTime dueDate) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));


        boolean isBorrowed = borrowedBookRepository.existsByBookCopy(copy);
        if (isBorrowed) {
            throw new RuntimeException("Book copy is already borrowed");
        }

        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));

        BorrowedBook borrowed = new BorrowedBook();
        borrowed.setUser(user);
        borrowed.setBookCopy(copy);
        borrowed.setBorrowedAt(borrowDate);
        borrowed.setDueDate(dueDate);
        borrowedBookRepository.save(borrowed);

        BorrowedBook log = new BorrowedBook();
        log.setBookCopy(copy);
        log.setUser(user);
        log.setBorrowedAt(borrowDate);
        log.setDueDate(dueDate);
        borrowedBookRepository.save(log);

        return borrowed;
    }



    @Override
    public BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDate borrowDate, LocalDate dueDate) {
        return null;
    }

    @Override
    public void returnBook(Long borrowedBookId, LocalDate returnDate) {
        BorrowedBook borrowed = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException(borrowedBookId));

        borrowedBookRepository.delete(borrowed);

        BookBorrowLog log = borrowLogRepository.findByBookCopyAndUserAndReturnedAtIsNull(
                        borrowed.getBookCopy(), borrowed.getUser())
                .orElseThrow(() -> new RuntimeException("Borrow log not found"));

        log.setReturnedAt(returnDate.atStartOfDay());
        borrowLogRepository.save(log);
    }


    @Override
    public List<BorrowedBook> getBorrowedBooksByUser(Long userId) {
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        return borrowedBookRepository.findByUser(user);
    }



    @Override
    public List<BookBorrowLog> getBorrowHistory(Long bookCopyId) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
        return borrowLogRepository.findByBookCopy(copy);
    }


    @Override
    public double calculateRentalFee(Long userId, int days) {
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        List<BorrowedBook> borrowed = borrowedBookRepository.findByUser(user);
        return borrowed.size() * days * 10.0;
    }


    @Override
    public boolean isBookAvailable(Long bookCopyId) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
        boolean isBorrowed = borrowedBookRepository.existsByBookCopy(copy);
        return !isBorrowed;
    }

}
