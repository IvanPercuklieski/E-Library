package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.model.domain.Review;

import java.util.List;

public interface ReviewDomainService {
    void addReview(Long bookId, Long userId, String text, int rating);

    List<Review> getReviewsForBook(Long bookId);

    Review getReviewEntityById(Long id);

    Review saveReviewEntity(Review review);

    void deleteReviewEntity(Review review);
}

