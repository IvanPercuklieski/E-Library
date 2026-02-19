package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookCopyDto;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.util.List;

public interface BookCopyApplicationService {
    DisplayBookCopyDto createBookCopy(CreateBookCopyDto bookCopy);
    void deleteBookCopy(Long bookCopyId);
    void deleteAllByBook(Long bookId);
    List<DisplayBookCopyDto> getAllBookCopies();
    List<DisplayBookCopyDto> getAllBookCopiesByBook(Long bookId);
    DisplayBookCopyDto getBookCopyById(Long bookCopyId);
}
