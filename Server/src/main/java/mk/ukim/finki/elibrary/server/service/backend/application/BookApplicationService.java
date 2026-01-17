package mk.ukim.finki.elibrary.server.service.backend.application;


import mk.ukim.finki.elibrary.server.dto.BookDetailsDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.util.List;


public interface BookApplicationService {

    BaseBook createBook(BaseBook book);

    BaseBook updateBook(BaseBook book);

    void deleteBook(Long bookId);

    BaseBook getBookById(Long bookId);

    List<BaseBook> getAllBooks();

    List<BaseBook> searchBooks(String title, Long authorId, List<Long> genreIds);

    List<BaseBook> getRecommendedBooksForUser(Long userId);

    List<BookCopy> getAvailableBookCopies(Long bookId);

    BookDetailsDto getBookDetails(Long bookId);

    void addReview(Long bookId, Long userId, String text, int rating);

    List<ReviewDisplayDto> getReviewsForBook(Long bookId);

}

