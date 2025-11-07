package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_book_id")
    private BaseBook baseBook;

    public BookCopy(Long id, BaseBook baseBook) {
        this.id = id;
        this.baseBook = baseBook;
    }

    public BookCopy(BaseBook baseBook) {
        this.baseBook = baseBook;
    }

    public BookCopy() {

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
}
