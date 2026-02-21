package mk.ukim.finki.elibrary.server.dto.display;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.time.LocalDateTime;
import java.util.List;

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
    public static List<DisplayBookBorrowLogDto> from(List<BookBorrowLog> logs){
        return logs.stream().map(DisplayBookBorrowLogDto::from).toList() ;
    }


}
