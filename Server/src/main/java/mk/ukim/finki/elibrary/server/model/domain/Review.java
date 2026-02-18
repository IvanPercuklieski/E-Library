package mk.ukim.finki.elibrary.server.model.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
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
}
