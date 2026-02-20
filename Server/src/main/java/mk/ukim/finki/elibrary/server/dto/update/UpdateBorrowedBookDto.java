package mk.ukim.finki.elibrary.server.dto.update;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.time.LocalDateTime;

public record UpdateBorrowedBookDto(
        Long userId,
        Long bookCopyId,
        LocalDateTime borrowedAt,
        LocalDateTime dueDate
) {
}
