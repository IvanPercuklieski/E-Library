package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
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
}
