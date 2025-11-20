package mk.ukim.finki.elibrary.server.dto;

import java.util.List;

public class BookRecommendationDTO {
    private Long userId;
    private List<BookSimpleDTO> recommendedBooks;

    public BookRecommendationDTO(Long userId, List<BookSimpleDTO> recommendedBooks) {
        this.userId = userId;
        this.recommendedBooks = recommendedBooks;
    }

    public Long getUserId() { return userId; }
    public List<BookSimpleDTO> getRecommendedBooks() { return recommendedBooks; }
}
