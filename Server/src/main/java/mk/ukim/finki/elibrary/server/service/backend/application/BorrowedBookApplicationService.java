package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;

import java.util.List;

public interface BorrowedBookApplicationService {
    DisplayBorrowedBookDto createBookBorrowing(CreateBorrowedBookDto dto);
    DisplayBorrowedBookDto updateBookBorrowing(Long id, UpdateBorrowedBookDto dto);
    void deleteBookBorrowing(CreateBookBorrowLogDto dto);
    List<DisplayBorrowedBookDto> getAllBookBorrowings();
    List<DisplayBorrowedBookDto> getAllBookBorrowingsByBook(Long bookId);
    List<DisplayBorrowedBookDto> getAllBookBorrowingsByUser(Long userId);
    DisplayBorrowedBookDto getById(Long borrowingId);
}
