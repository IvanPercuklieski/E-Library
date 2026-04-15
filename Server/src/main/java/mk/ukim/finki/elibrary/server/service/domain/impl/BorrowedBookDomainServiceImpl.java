package mk.ukim.finki.elibrary.server.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
import mk.ukim.finki.elibrary.server.model.domain.*;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.repository.*;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookDomainService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowedBookDomainServiceImpl implements BorrowedBookDomainService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final UserWrapperRepository userWrapperRepository;
    private final BorrowedBookLogRepository borrowedBookLogRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BorrowedBookDomainServiceImpl(
            BorrowedBookRepository borrowedBookRepository,
            BookCopyRepository bookCopyRepository,
            UserWrapperRepository userWrapperRepository,
            BorrowedBookLogRepository borrowedBookLogRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.borrowedBookLogRepository = borrowedBookLogRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public BorrowedBook createBookBorrowing(CreateBorrowedBookDto dto) {

        BookCopy copy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(() -> new BookCopyNotFoundException(dto.bookCopyId()));

        if (!Boolean.TRUE.equals(copy.getIsAvailable())) {
            throw new IllegalStateException();
        }

        UserWrapper user = userWrapperRepository.findById(dto.userId())
                .orElseThrow(() -> new UserWrapperNotFoundException(dto.userId()));

        BorrowedBook borrowing = new BorrowedBook(
                dto.borrowedAt(),
                dto.dueDate(),
                user,
                copy
        );

        copy.setIsAvailable(false);

        bookCopyRepository.save(copy);
        return borrowedBookRepository.save(borrowing);
    }

    @Transactional
    @Override
    public BorrowedBook updateBookBorrowing(Long id, UpdateBorrowedBookDto dto) {

        BorrowedBook borrowing = borrowedBookRepository.findById(id)
                .orElseThrow(() -> new BorrowedBookNotFoundException(id));

        UserWrapper user = userWrapperRepository.findById(dto.userId())
                .orElseThrow(() -> new UserWrapperNotFoundException(dto.userId()));

        BookCopy oldCopy = borrowing.getBookCopy();

        BookCopy newCopy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(() -> new BookCopyNotFoundException(dto.bookCopyId()));

        if (!oldCopy.getId().equals(newCopy.getId())) {

            if (!Boolean.TRUE.equals(newCopy.getIsAvailable())) {
                throw new IllegalStateException();
            }

            oldCopy.setIsAvailable(true);
            newCopy.setIsAvailable(false);

            borrowing.setBookCopy(newCopy);

            bookCopyRepository.save(oldCopy);
            bookCopyRepository.save(newCopy);
        }

        borrowing.setUser(user);
        borrowing.setBorrowedAt(dto.borrowedAt());
        borrowing.setDueDate(dto.dueDate());

        return borrowedBookRepository.save(borrowing);
    }

    @Transactional
    @Override
    public void deleteBookBorrowing(Long borrowingId) {

        BorrowedBook borrowing = borrowedBookRepository.findById(borrowingId)
                .orElseThrow(() -> new BorrowedBookNotFoundException(borrowingId));

        BookCopy copy = borrowing.getBookCopy();

        BookBorrowLog log = new BookBorrowLog();
        log.setBookCopy(copy);
        log.setUser(borrowing.getUser());
        log.setBorrowedAt(borrowing.getBorrowedAt());
        log.setDueDate(borrowing.getDueDate());
        log.setReturnedAt(LocalDateTime.now());
        log.setNotes(null);

        borrowedBookLogRepository.save(log);

        borrowedBookRepository.delete(borrowing);

        copy.setIsAvailable(true);
        bookCopyRepository.save(copy);

        eventPublisher.publishEvent(
                new BookBorrowLogCreatedEvent(log.getId(), log.getUser().getId())
        );
    }

    @Override
    public List<BorrowedBook> getAllBookBorrowings() {
        return borrowedBookRepository.findAll();
    }

    @Override
    public List<BorrowedBook> getAllBookBorrowingsByBook(Long bookId) {
        return borrowedBookRepository.findAllByBookCopy_BaseBook_Id(bookId);
    }

    @Override
    public List<BorrowedBook> getAllBookBorrowingsByUser(Long userId) {
        UserWrapper user = userWrapperRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));

        return borrowedBookRepository.findByUser(user);
    }

    @Override
    public BorrowedBook getById(Long borrowingId) {
        return borrowedBookRepository.findById(borrowingId)
                .orElseThrow(() -> new BorrowedBookNotFoundException(borrowingId));
    }
}