package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;

import java.time.LocalDateTime;

public interface BorrowedBookLogService {
    BookBorrowLog createBorrowLog(Long userId,
                                  Long bookCopyId,
                                  LocalDateTime borrowedAt,
                                  LocalDateTime dueDate,
                                  String notes);
}
