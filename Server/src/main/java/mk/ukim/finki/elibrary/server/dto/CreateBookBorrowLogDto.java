package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import java.time.LocalDateTime;

public record CreateBookBorrowLogDto(
                                     Long userId,Long bookCopyId,
                                     LocalDateTime borrowedAt,
                                     LocalDateTime returnedAt,
                                     LocalDateTime dueDate,
                                     String notes) {

//    public BookBorrowLog toBorrowLog(BookCopy bookCopy, UserWrapper user) {
//        return new BookBorrowLog(bookCopyId,borrowedAt,returnedAt,dueDate,notes,);
//    }

//    @Override
//    public Long userId() {
//        return userId;
//    }

//    @Override
//    public Long bookCopyId() {
//        return bookCopyId;
//    }
//
//    @Override
//    public LocalDateTime borrowedAt() {
//        return borrowedAt;
//    }
//
//    @Override
//    public LocalDateTime dueDate() {
//        return dueDate;
//    }
//
//    @Override
//    public String notes() {
//        return notes;
//    }
}
