package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class BookBorrowLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime borrowedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime returnedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dueDate;

    @Column(columnDefinition = "TEXT")
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
}
