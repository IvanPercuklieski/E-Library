package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;

import java.util.List;

import mk.ukim.finki.elibrary.server.dto.update.UpdateReviewDto;

public interface ReviewApplicationService {

    void addReview(Long bookId, Long userId, String text, int rating);

    List<ReviewDisplayDto> getReviewsForBook(Long bookId);

    ReviewDisplayDto updateReview(Long id, UpdateReviewDto dto);

    void deleteReview(Long id);
}

