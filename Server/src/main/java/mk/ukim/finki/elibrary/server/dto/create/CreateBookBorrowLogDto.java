package mk.ukim.finki.elibrary.server.dto.create;

import java.time.LocalDateTime;

public record CreateBookBorrowLogDto(
                                     Long bookBorrowingId,
                                     LocalDateTime borrowedAt,
                                     LocalDateTime returnedAt,
                                     LocalDateTime dueDate,
                                     String notes) {

}
