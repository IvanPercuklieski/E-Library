package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
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

    @Column(length = 1000)
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

    public BaseBook() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public int getNumBooks() {
        return numBooks;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumBooks(int numBooks) {
        this.numBooks = numBooks;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }
}
