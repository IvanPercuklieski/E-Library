package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.Review;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReviewDisplayDto(
        Long id,
        String bookTitle,
        String userFullName,
        String text,
        int rating,
        LocalDateTime createdAt
) {

    public static ReviewDisplayDto from(Review review) {
        return new ReviewDisplayDto(
                review.getId(),
                review.getBook().getTitle(),
                review.getUser().getName() + " " + review.getUser().getSurname(),
                review.getText(),
                review.getRating(),
                review.getCreatedAt()
        );
    }

    public static List<ReviewDisplayDto> from(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewDisplayDto::from)
                .collect(Collectors.toList());
    }
}
