package mk.ukim.finki.elibrary.server.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.exceptions.BookCopyNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.UserWrapperNotFoundException;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookLogDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedBookLogDomainServiceImpl implements BorrowedBookLogDomainService {

   private final BorrowedBookLogRepository borrowedBookLogRepository;
    private final UserWrapperRepository userWrapperRepository;
    private final BookCopyRepository bookCopyRepository;

    public BorrowedBookLogDomainServiceImpl(BorrowedBookLogRepository borrowedBookLogRepository, UserWrapperRepository userWrapperRepository, BookCopyRepository bookCopyRepository) {
        this.borrowedBookLogRepository = borrowedBookLogRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<BookBorrowLog> getAll() {
        return borrowedBookLogRepository.findAll();
    }

    @Override
    public List<BookBorrowLog> getAllForUser(Long userId) {
        UserWrapper userWrapper=userWrapperRepository.findById(userId).orElseThrow(()->new UserWrapperNotFoundException(userId));
        return borrowedBookLogRepository.findByUser(userWrapper);
    }

    @Override
    public List<BookBorrowLog> getAllForBookCopy(Long bookCopyId) {
        BookCopy copy= bookCopyRepository.findById(bookCopyId).orElseThrow(()->new BookCopyNotFoundException(bookCopyId));
        return borrowedBookLogRepository.getBookBorrowLogsByBookCopy(copy);
    }

    @Override
    @Transactional
    public void deleteAllForBookCopy(Long bookCopyId) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));

        borrowedBookLogRepository.deleteAllByBookCopy(copy);
    }

    @Override
    @Transactional
    public void deleteAllForUser(Long userId) {
        UserWrapper user = userWrapperRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));

        borrowedBookLogRepository.deleteAllByUser(user);
    }

    @Override
    @Transactional
    public void deleteAll() {
        borrowedBookLogRepository.deleteAll();
    }
}
