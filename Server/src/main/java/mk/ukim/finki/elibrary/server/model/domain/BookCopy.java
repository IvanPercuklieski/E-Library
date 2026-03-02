package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookcopies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_book_id", nullable = false)
    private BaseBook baseBook;

    @OneToMany(
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookBorrowLog> logs = new ArrayList<>();

    @OneToOne(
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private BorrowedBook borrowedBook;

    @Column
    private Boolean isAvailable;

    public BookCopy(BaseBook b) {
        this.baseBook = b;
        this.isAvailable=true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseBook getBaseBook() {
        return baseBook;
    }

    public void setBaseBook(BaseBook baseBook) {
        this.baseBook = baseBook;
    }

    public List<BookBorrowLog> getLogs() {
        return logs;
    }

    public void setLogs(List<BookBorrowLog> logs) {
        this.logs = logs;
    }

    public BorrowedBook getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(BorrowedBook borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
}
