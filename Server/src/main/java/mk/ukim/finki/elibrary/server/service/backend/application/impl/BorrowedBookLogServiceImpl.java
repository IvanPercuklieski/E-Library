package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.BorrowedBookLogService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BorrowedBookLogServiceImpl implements BorrowedBookLogService {
    private final BorrowedBookLogRepository bookBorrowLogRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserWrapperRepository userWrapperRepository;
    private final BookCopyRepository bookCopyRepository;


    public BorrowedBookLogServiceImpl(BorrowedBookLogRepository bookBorrowLogRepository,
                                    UserWrapperRepository userWrapperRepository,
                                    BookCopyRepository bookCopyRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.bookBorrowLogRepository = bookBorrowLogRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public BookBorrowLog createBorrowLog(Long userId,
                                         Long bookCopyId,
                                         LocalDateTime borrowedAt,
                                         LocalDateTime dueDate,
                                         String notes) {

        UserWrapper user = userWrapperRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new IllegalArgumentException("Book copy not found: " + bookCopyId));

        BookBorrowLog log = new BookBorrowLog();
        log.setUser(user);
        log.setBookCopy(copy);
        log.setBorrowedAt(borrowedAt);
        log.setDueDate(dueDate);
        log.setNotes(notes != null ? notes : "Created via API");

        BookBorrowLog saved = bookBorrowLogRepository.save(log);


        eventPublisher.publishEvent(
                new BookBorrowLogCreatedEvent(saved.getId(), userId)
        );

        return saved;
    }
}
