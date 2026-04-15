package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookcopies")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "base_book_id", nullable = false)
    private BaseBook baseBook;

    // FULL HISTORY OF BORROWS (NOT ONLY ONE)
    @OneToMany(
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BorrowedBook> borrowHistory = new ArrayList<>();

    @OneToMany(
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BookBorrowLog> logs = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isAvailable = true;

    public BookCopy() {}

    public BookCopy(BaseBook baseBook) {
        this.baseBook = baseBook;
        this.isAvailable = true;
    }

    public Long getId() {
        return id;
    }

    public BaseBook getBaseBook() {
        return baseBook;
    }

    public void setBaseBook(BaseBook baseBook) {
        this.baseBook = baseBook;
    }

    public List<BorrowedBook> getBorrowHistory() {
        return borrowHistory;
    }

    public void setBorrowHistory(List<BorrowedBook> borrowHistory) {
        this.borrowHistory = borrowHistory;
    }

    public List<BookBorrowLog> getLogs() {
        return logs;
    }

    public void setLogs(List<BookBorrowLog> logs) {
        this.logs = logs;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        this.isAvailable = available;
    }
}