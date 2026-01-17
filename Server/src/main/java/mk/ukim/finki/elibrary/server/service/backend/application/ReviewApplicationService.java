package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;

import java.util.List;

public interface ReviewApplicationService {
    void addReview(Long bookId, Long userId, String text, int rating);
    List<ReviewDisplayDto> getReviewsForBook(Long bookId);
}

