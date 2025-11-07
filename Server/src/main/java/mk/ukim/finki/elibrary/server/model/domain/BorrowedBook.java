package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime borrowedAt;
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopy bookCopy;

    public BorrowedBook(Long id, LocalDateTime borrowedAt, LocalDateTime dueDate, UserWrapper user, BookCopy bookCopy) {
        this.id = id;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.user = user;
        this.bookCopy = bookCopy;
    }

    public BorrowedBook(LocalDateTime borrowedAt, LocalDateTime dueDate, UserWrapper user, BookCopy bookCopy) {
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.user = user;
        this.bookCopy = bookCopy;
    }

    public BorrowedBook() {

    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public UserWrapper getUser() {
        return user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setUser(UserWrapper user) {
        this.user = user;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }
}
