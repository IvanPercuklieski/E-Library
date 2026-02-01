package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
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
}
