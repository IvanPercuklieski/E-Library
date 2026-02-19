package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.util.List;

public interface BookCopyDomainService {
    BookCopy createBookCopy(CreateBookCopyDto bookCopy);
    void deleteBookCopy(Long bookCopyId);
    void deleteAllByBook(Long bookId);
    List<BookCopy> getAllBookCopies();
    List<BookCopy> getAllBookCopiesByBook(Long bookId);
    BookCopy getBookCopyById(Long bookCopyId);
}
