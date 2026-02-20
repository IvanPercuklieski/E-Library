package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;

import java.util.List;

public interface BorrowedBookDomainService {
    BorrowedBook createBookBorrowing(CreateBorrowedBookDto dto);
    BorrowedBook updateBookBorrowing(Long id, UpdateBorrowedBookDto dto);
    void deleteBookBorrowing(CreateBookBorrowLogDto dto );
    List<BorrowedBook> getAllBookBorrowings();
    List<BorrowedBook> getAllBookBorrowingsByBook(Long bookId);
    List<BorrowedBook> getAllBookBorrowingsByUser(Long userId);
    BorrowedBook getById(Long borrowingId);
}
