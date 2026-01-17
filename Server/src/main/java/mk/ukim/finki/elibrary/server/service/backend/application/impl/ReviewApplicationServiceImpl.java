package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.dto.ReviewCreateDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.model.domain.Review;
import mk.ukim.finki.elibrary.server.service.backend.application.ReviewApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.ReviewDomainService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class ReviewApplicationServiceImpl implements ReviewApplicationService {

    private final ReviewDomainService reviewDomainService;

    public ReviewApplicationServiceImpl(ReviewDomainService reviewDomainService) {
        this.reviewDomainService = reviewDomainService;
    }

    @Override
    public void addReview(Long bookId, Long userId, String text, int rating) {
        reviewDomainService.addReview(bookId, userId, text, rating);
    }


    @Override
    public List<ReviewDisplayDto> getReviewsForBook(Long bookId) {
        List<Review> reviews = reviewDomainService.getReviewsForBook(bookId);
        return ReviewDisplayDto.from(reviews);
    }
}
