package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;

import java.time.LocalDateTime;

public record DisplayBookBorrowLogDto(Long id,
                                      Long userId,
                                      Long bookCopyId,
                                      LocalDateTime borrowedAt,
                                      LocalDateTime returnedAt,
                                      LocalDateTime dueDate,
                                      String notes) {

    public static DisplayBookBorrowLogDto from(BookBorrowLog borrowlog) {
        return new DisplayBookBorrowLogDto(
                borrowlog.getId(),
                borrowlog.getUser().getId(),
                borrowlog.getBookCopy().getId(),
                borrowlog.getBorrowedAt(),
                borrowlog.getReturnedAt(),
                borrowlog.getDueDate(),
                borrowlog.getNotes()
        );
    }


}
