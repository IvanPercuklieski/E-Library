package mk.ukim.finki.elibrary.server.service.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBaseBookDto;
import mk.ukim.finki.elibrary.server.model.domain.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookDomainService {

        BaseBook createBook(BaseBook book);

        BaseBook updateBook(Long bookId,UpdateBaseBookDto book);

        void deleteBook(Long bookId);

        BaseBook getBookById(Long bookId);

        List<BaseBook> getAllBooks();

        BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDateTime borrowDate, LocalDateTime dueDate);

        void returnBook(Long borrowedBookId, LocalDate returnDate);

        List<BookCopy> getAvailableBookCopies(Long bookId);

        List<BaseBook> getRecommendedBooksForUser(Long userId);

        List<BaseBook> searchBooks(String title, Long authorId, List<Long> genreIds);

        List<BorrowedBook> getBorrowedBooksByUser(Long userId);

        List<BookBorrowLog> getBorrowHistory(Long bookCopyId);

        double calculateRentalFee(Long userId, int days);

        boolean isBookAvailable(Long bookCopyId);

        List<BaseBook> getBooksByAuthor(Long authorId);

        List<BaseBook> getBooksByGenre(Long genreId);

        void addReview(Long bookId, Long userId, String text, int rating);
        long countTotalCopies(Long bookId);
        long countActiveBorrowings(Long bookId);

}
