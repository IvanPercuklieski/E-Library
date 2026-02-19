package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "borrowed_books")
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime borrowedAt;

    @Column
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_copy_id", nullable = false, unique = true)
    private BookCopy bookCopy;

    public BorrowedBook(LocalDateTime borrowedAt, LocalDateTime dueDate, UserWrapper user, BookCopy bookCopy) {
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.user = user;
        this.bookCopy = bookCopy;
    }
}
