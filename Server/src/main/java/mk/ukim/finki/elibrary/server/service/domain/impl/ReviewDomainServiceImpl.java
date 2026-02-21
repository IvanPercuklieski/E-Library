package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Review;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.BaseBookRepository;
import mk.ukim.finki.elibrary.server.repository.ReviewRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.ReviewDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewDomainServiceImpl implements ReviewDomainService {

    private final BaseBookRepository bookRepository;
    private final UserWrapperRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewDomainServiceImpl(BaseBookRepository bookRepository,
                                   UserWrapperRepository userRepository,
                                   ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void addReview(Long bookId, Long userId, String text, int rating) {
        BaseBook book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review(book, user, text, rating);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    @Override
    public Review getReviewEntityById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public Review saveReviewEntity(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReviewEntity(Review review) {
        reviewRepository.delete(review);
    }


}
