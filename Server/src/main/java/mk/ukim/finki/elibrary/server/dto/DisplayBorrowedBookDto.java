package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BorrowedBook;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayBorrowedBookDto(Long id,
                                     Long userId,
                                     Long bookCopyId,
                                     String bookTitle,
                                     LocalDateTime borrowedAt,
                                     LocalDateTime dueDate) {

    public static DisplayBorrowedBookDto from(BorrowedBook borrowedBook) {
        return new DisplayBorrowedBookDto(
                borrowedBook.getId(),
                borrowedBook.getUser().getId(),
                borrowedBook.getBookCopy().getId(),
                borrowedBook.getBookCopy().getBaseBook().getTitle(),
                borrowedBook.getBorrowedAt(),
                borrowedBook.getDueDate()
        );
    }

    public static List<DisplayBorrowedBookDto> from(List<BorrowedBook> borrowedBooks) {
        return borrowedBooks.stream()
                .map(DisplayBorrowedBookDto::from)
                .collect(Collectors.toList());
    }
}
