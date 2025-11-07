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

    @Column(columnDefinition = "TIMESTAMP")
    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }
    @Column(columnDefinition = "TIMESTAMP")
    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }
    @Column(columnDefinition = "TIMESTAMP")
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Long getId() {
        return id;
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

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public void setUser(UserWrapper user) {
        this.user = user;
    }
}
