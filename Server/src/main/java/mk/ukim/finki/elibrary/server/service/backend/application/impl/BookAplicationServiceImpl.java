package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import jakarta.transaction.Transactional;

import mk.ukim.finki.elibrary.server.dto.BookDetailsDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import mk.ukim.finki.elibrary.server.model.domain.Review;
import mk.ukim.finki.elibrary.server.service.backend.application.BookApplicationService;
import mk.ukim.finki.elibrary.server.service.backend.application.ReviewApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BookDomainService;
import mk.ukim.finki.elibrary.server.service.domain.ReviewDomainService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class BookAplicationServiceImpl implements BookApplicationService {

    private final BookDomainService bookDomainService;
    private final ReviewApplicationService reviewApplicationService;
    private final ReviewDomainService reviewDomainService;

    public BookAplicationServiceImpl(BookDomainService bookDomainService, ReviewApplicationService reviewApplicationService, ReviewDomainService reviewDomainService) {
        this.bookDomainService = bookDomainService;
        this.reviewApplicationService = reviewApplicationService;
        this.reviewDomainService = reviewDomainService;
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

    @Override
    public BookDetailsDto getBookDetails(Long bookId) {
        BaseBook book = bookDomainService.getBookById(bookId);
        int availableCopies = bookDomainService.getAvailableBookCopies(bookId).size();

        List<ReviewDisplayDto> reviews = reviewApplicationService.getReviewsForBook(bookId);

        return new BookDetailsDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenres().stream().map(Genre::getName).toList(),
                book.getPubDate(),
                book.getDescription(),
                availableCopies,
                reviews
        );
    }

    @Override
    public void addReview(Long bookId, Long userId, String text, int rating) {
        reviewApplicationService.addReview(bookId, userId, text, rating);

    }

    @Override
    public List<ReviewDisplayDto> getReviewsForBook(Long bookId) {
        List<Review> reviews = reviewDomainService.getReviewsForBook(bookId);
        return ReviewDisplayDto.from(reviews);
    }
}
