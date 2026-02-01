package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class BaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    private LocalDate pubDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int numBooks;

    @OneToMany(mappedBy = "baseBook")
    private List<BookCopy> copies;

    public BaseBook(String title, Author author, List<Genre> genres,
                    LocalDate pubDate, String description, int numBooks) {
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.pubDate = pubDate;
        this.description = description;
        this.numBooks = numBooks;
    }

    public BaseBook(Long id, String title, Author author, List<Genre> genres,
                    LocalDate pubDate, String description, int numBooks, List<BookCopy> copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.pubDate = pubDate;
        this.description = description;
        this.numBooks = numBooks;
        this.copies = copies;
    }
}
