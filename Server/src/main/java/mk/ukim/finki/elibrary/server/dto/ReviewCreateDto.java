package mk.ukim.finki.elibrary.server.dto;


import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Review;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

public record ReviewCreateDto(
        Long bookId,
        Long userId,
        String text,
        int rating
) {
    public Review toReview(UserWrapper user, BaseBook book) {
        return new Review(book, user, text, rating);
    }
}