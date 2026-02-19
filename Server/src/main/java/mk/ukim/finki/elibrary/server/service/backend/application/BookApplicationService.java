package mk.ukim.finki.elibrary.server.service.backend.application;


import mk.ukim.finki.elibrary.server.dto.BookDetailsDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBaseDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBaseBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBaseBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBaseBookDto;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.util.List;


public interface BookApplicationService {

    DisplayBookBaseDto createBook(CreateBaseBookDto book);

    DisplayBaseBookDto updateBook(Long bookId,UpdateBaseBookDto book);

    void deleteBook(Long bookId);

    DisplayBaseBookDto getBookById(Long bookId);

    List<DisplayBaseBookDto> getAllBooks();

    List<DisplayBaseBookDto> searchBooks(String title, Long authorId, List<Long> genreIds);

    List<BaseBook> getRecommendedBooksForUser(Long userId);

    List<BookCopy> getAvailableBookCopies(Long bookId);

    BookDetailsDto getBookDetails(Long bookId);

    void addReview(Long bookId, Long userId, String text, int rating);

    List<ReviewDisplayDto> getReviewsForBook(Long bookId);

}

