package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.BorrowedBookLogApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookLogDomainService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowedBookLogApplicationServiceImpl implements BorrowedBookLogApplicationService {

    private final BorrowedBookLogDomainService borrowedBookLogDomainService;

    public BorrowedBookLogApplicationServiceImpl( BorrowedBookLogDomainService borrowedBookLogDomainService) {

        this.borrowedBookLogDomainService = borrowedBookLogDomainService;
    }


//    @Transactional
//    public BookBorrowLog createBorrowLog(Long userId,
//                                         Long bookCopyId,
//                                         LocalDateTime borrowedAt,
//                                         LocalDateTime dueDate,
//                                         String notes) {
//
//        UserWrapper user = userWrapperRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//
//        BookCopy copy = bookCopyRepository.findById(bookCopyId)
//                .orElseThrow(() -> new IllegalArgumentException("Book copy not found: " + bookCopyId));
//
//        BookBorrowLog log = new BookBorrowLog();
//        log.setUser(user);
//        log.setBookCopy(copy);
//        log.setBorrowedAt(borrowedAt);
//        log.setDueDate(dueDate);
//        log.setNotes(notes != null ? notes : "Created via API");
//
//        BookBorrowLog saved = bookBorrowLogRepository.save(log);
//
//
//        eventPublisher.publishEvent(
//                new BookBorrowLogCreatedEvent(saved.getId(), userId)
//        );
//
//        return saved;
//    }

    @Override
    public List<DisplayBookBorrowLogDto> getAll() {
        return DisplayBookBorrowLogDto.from(borrowedBookLogDomainService.getAll());
    }

    @Override
    public List<DisplayBookBorrowLogDto> getAllForUser(Long userId) {
        return DisplayBookBorrowLogDto.from(borrowedBookLogDomainService.getAllForUser(userId));
    }

    @Override
    public List<DisplayBookBorrowLogDto> getAllForBookCopy(Long bookCopyId) {
        return DisplayBookBorrowLogDto.from(borrowedBookLogDomainService.getAllForBookCopy(bookCopyId));
    }

    @Override
    public void deleteAllForBookCopy(Long bookCopyId) {
        borrowedBookLogDomainService.deleteAllForBookCopy(bookCopyId);
    }

    @Override
    public void deleteAllForUser(Long userId) {
        borrowedBookLogDomainService.deleteAllForUser(userId);
    }

    @Override
    public void deleteAll() {
        borrowedBookLogDomainService.deleteAll();
    }
}
