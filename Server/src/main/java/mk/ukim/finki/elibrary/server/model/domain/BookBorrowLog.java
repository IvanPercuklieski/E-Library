package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BookBorrowLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LocalDateTime dueDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    public BookBorrowLog(Long id, LocalDateTime borrowedAt, LocalDateTime returnedAt, LocalDateTime dueDate, String notes, BookCopy bookCopy, UserWrapper user) {
        this.id = id;
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.dueDate = dueDate;
        this.notes = notes;
        this.bookCopy = bookCopy;
        this.user = user;
    }

    public BookBorrowLog(LocalDateTime borrowedAt, LocalDateTime returnedAt, LocalDateTime dueDate, String notes, BookCopy bookCopy, UserWrapper user) {
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.dueDate = dueDate;
        this.notes = notes;
        this.bookCopy = bookCopy;
        this.user = user;
    }

    public BookBorrowLog() {

    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public UserWrapper getUser() {
        return user;
    }
}
