package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import jakarta.transaction.Transactional;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.service.backend.application.BookApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BookDomainService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class BookAplicationServiceImpl implements BookApplicationService {

    private final BookDomainService bookDomainService;

    public BookAplicationServiceImpl(BookDomainService bookDomainService) {
        this.bookDomainService = bookDomainService;
    }

    @Override
    public BaseBook createBook(BaseBook book) {
        return bookDomainService.createBook(book);
    }

    @Override
    public BaseBook updateBook(BaseBook book) {
        return bookDomainService.updateBook(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDomainService.deleteBook(bookId);
    }

    @Override
    public BaseBook getBookById(Long bookId) {
        return bookDomainService.getBookById(bookId);
    }

    @Override
    public List<BaseBook> getAllBooks() {
        return bookDomainService.getAllBooks();
    }

    @Override
    public List<BaseBook> searchBooks(String title, Long authorId, List<Long> genreIds) {
        return bookDomainService.searchBooks(title, authorId, genreIds);
    }

    @Override
    public List<BaseBook> getRecommendedBooksForUser(Long userId) {
        return bookDomainService.getRecommendedBooksForUser(userId);
    }

    @Override
    public List<BookCopy> getAvailableBookCopies(Long bookId) {
        return bookDomainService.getAvailableBookCopies(bookId);
    }
}
