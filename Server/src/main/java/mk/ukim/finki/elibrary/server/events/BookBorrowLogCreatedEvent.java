package mk.ukim.finki.elibrary.server.events;

public class BookBorrowLogCreatedEvent {

    private final Long bookBorrowLogId;
    private final Long userId;

    public BookBorrowLogCreatedEvent(Long bookBorrowLogId, Long userId) {
        this.bookBorrowLogId = bookBorrowLogId;
        this.userId = userId;
    }

    public Long getBookBorrowLogId() {
        return bookBorrowLogId;
    }

    public Long getUserId() {
        return userId;
    }
}