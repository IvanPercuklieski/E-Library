package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import java.util.List;

public interface BorrowedBookLogDomainService {
    List<BookBorrowLog>getAll();
    List<BookBorrowLog>getAllForUser(Long userId);
    List<BookBorrowLog>getAllForBookCopy(Long bookCopyId);
    void deleteAllForBookCopy(Long bookCopyId);
    void deleteAllForUser(Long userId);
    void deleteAll();
}
