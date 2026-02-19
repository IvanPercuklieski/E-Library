package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookCopyDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;

import java.util.List;

public interface BorrowedBookDomainService {
    DisplayBorrowedBookDto createBookBorrowing(CreateBorrowedBookDto bookCopy);
    DisplayBorrowedBookDto updateBookBorrowing(Long id, UpdateBorrowedBookDto bookCopy);
    void deleteBookBorrowing(Long bookCopyId);
    List<DisplayBorrowedBookDto> getAllBookBorrowings();
    List<DisplayBorrowedBookDto> getAllBookBorrowingsByBook(Long bookId);
    List<DisplayBorrowedBookDto> getAllBookBorrowingsByUser(Long bookCopyId);
    DisplayBorrowedBookDto getById(Long borrowingId);
}
