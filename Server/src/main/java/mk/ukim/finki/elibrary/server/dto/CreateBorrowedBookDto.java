package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.time.LocalDateTime;

public record CreateBorrowedBookDto(Long userId,
                                    Long bookCopyId,
                                    LocalDateTime borrowedAt,
                                    LocalDateTime dueDate) {

    public BorrowedBook toBorrowedBook(UserWrapper user, BookCopy bookCopy) {
        return new BorrowedBook(borrowedAt, dueDate, user, bookCopy);
    }


}
