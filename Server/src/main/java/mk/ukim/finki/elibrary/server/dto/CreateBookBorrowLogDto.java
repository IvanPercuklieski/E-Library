package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import java.time.LocalDateTime;

public record CreateBookBorrowLogDto(Long userId,
                                     Long bookCopyId,
                                     LocalDateTime borrowedAt,
                                     LocalDateTime dueDate,
                                     String notes) {

    public BookBorrowLog toBorrowLog(BookCopy bookCopy, UserWrapper user) {
        return new BookBorrowLog(null, borrowedAt, null, dueDate, notes, bookCopy, user);
    }
}
