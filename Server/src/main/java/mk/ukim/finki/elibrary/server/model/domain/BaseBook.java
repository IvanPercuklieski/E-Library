package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BaseBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Column
    private LocalDate pubDate;

    @Column(length = 1000)
    private String description;

    @Column
    private int numBooks;

    @OneToMany(mappedBy = "baseBook", cascade = CascadeType.ALL, orphanRemoval = true)
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
}
