package mk.ukim.finki.elibrary.server.model.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BaseBook book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    private String text;

    private int rating;

    private LocalDateTime createdAt;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    public Review(BaseBook book, UserWrapper user, String text, int rating) {
        this.book = book;
        this.user = user;
        this.text = text;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public BaseBook getBook() { return book; }
    public UserWrapper getUser() { return user; }
    public String getText() { return text; }
    public int getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setBook(BaseBook book) { this.book = book; }
    public void setUser(UserWrapper user) { this.user = user; }
    public void setText(String text) { this.text = text; }
    public void setRating(int rating) { this.rating = rating; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
