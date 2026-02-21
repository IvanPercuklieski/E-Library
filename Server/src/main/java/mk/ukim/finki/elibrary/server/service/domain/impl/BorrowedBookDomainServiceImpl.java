package mk.ukim.finki.elibrary.server.service.domain.impl;

import org.springframework.transaction.annotation.Transactional;
import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
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
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookDomainService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
@Service
public class BorrowedBookDomainServiceImpl implements BorrowedBookDomainService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final UserWrapperRepository userWrapperRepository;
    private final BorrowedBookLogRepository borrowedBookLogRepository;
    private final ApplicationEventPublisher eventPublisher;
    public BorrowedBookDomainServiceImpl(BorrowedBookRepository borrowedBookRepository, BookCopyRepository bookCopyRepository, UserWrapperRepository userWrapperRepository, BorrowedBookLogRepository borrowedBookLogRepository, ApplicationEventPublisher eventPublisher) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.borrowedBookLogRepository = borrowedBookLogRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public BorrowedBook createBookBorrowing(CreateBorrowedBookDto bookCopy) {
        BookCopy copy= bookCopyRepository.findById(bookCopy.bookCopyId()).orElseThrow(()->new BookCopyNotFoundException(bookCopy.bookCopyId()));
        UserWrapper userWrapper=userWrapperRepository.findById(bookCopy.userId()).orElseThrow(()->new UserWrapperNotFoundException(bookCopy.userId()));

        BorrowedBook borrowing=new BorrowedBook();
        borrowing.setBorrowedAt(bookCopy.borrowedAt());
        borrowing.setDueDate(bookCopy.dueDate());
        borrowing.setBookCopy(copy);
        copy.setBorrowedBook(borrowing);
        borrowing.setUser(userWrapper);
        copy.setIsAvailable(false);
        bookCopyRepository.save(copy);
        return borrowedBookRepository.save(borrowing);

    }

    @Transactional
    @Override
    public BorrowedBook updateBookBorrowing(Long id, UpdateBorrowedBookDto dto) {

        BorrowedBook borrowing = borrowedBookRepository.findById(id)
                .orElseThrow(() -> new BorrowedBookNotFoundException(id));

        UserWrapper userWrapper = userWrapperRepository.findById(dto.userId())
                .orElseThrow(() -> new UserWrapperNotFoundException(dto.userId()));

        BookCopy newCopy = bookCopyRepository.findById(dto.bookCopyId())
                .orElseThrow(() -> new BookCopyNotFoundException(dto.bookCopyId()));

        BookCopy oldCopy = borrowing.getBookCopy();


        if (oldCopy != null && !oldCopy.getId().equals(newCopy.getId())) {

            if (Boolean.FALSE.equals(newCopy.getIsAvailable())) {
                throw new BookCopyNotFoundException(newCopy.getId());
            }

            oldCopy.setIsAvailable(true);
            oldCopy.setBorrowedBook(null);


            newCopy.setIsAvailable(false);
            newCopy.setBorrowedBook(borrowing);

            borrowing.setBookCopy(newCopy);

            bookCopyRepository.save(oldCopy);
            bookCopyRepository.save(newCopy);
        }

        borrowing.setUser(userWrapper);
        borrowing.setBorrowedAt(dto.borrowedAt());
        borrowing.setDueDate(dto.dueDate());

        return borrowedBookRepository.save(borrowing);
    }

    @Transactional
    @Override
    public void deleteBookBorrowing(CreateBookBorrowLogDto dto) {

        BorrowedBook borrowing = borrowedBookRepository.findById(dto.bookBorrowingId())
                .orElseThrow(() -> new BorrowedBookNotFoundException(dto.bookBorrowingId()));

        BookCopy copy = borrowing.getBookCopy();

        copy.setIsAvailable(true);
        copy.setBorrowedBook(null);
        borrowing.setBookCopy(null);
        bookCopyRepository.save(copy);

        BookBorrowLog log = new BookBorrowLog();
        log.setBookCopy(copy);
        log.setUser(borrowing.getUser());

        log.setBorrowedAt(dto.borrowedAt() != null ? dto.borrowedAt() : borrowing.getBorrowedAt());
        log.setDueDate(dto.dueDate() != null ? dto.dueDate() : borrowing.getDueDate());
        log.setReturnedAt(dto.returnedAt() != null ? dto.returnedAt() : LocalDateTime.now());
        log.setNotes(dto.notes());

        BookBorrowLog saved = borrowedBookLogRepository.save(log);
        eventPublisher.publishEvent(
                new BookBorrowLogCreatedEvent(saved.getId(), saved.getUser().getId())
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
        UserWrapper userWrapper = userWrapperRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        return borrowedBookRepository.findByUser(userWrapper);
    }

    @Override
    public BorrowedBook getById(Long borrowingId) {
        return borrowedBookRepository.findById(borrowingId).orElseThrow(() -> new BorrowedBookNotFoundException(borrowingId));
    }
}
