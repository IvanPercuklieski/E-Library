package mk.ukim.finki.elibrary.server.dto;

import java.util.List;

public class UserGenreRecommendationDTO {
    private Long userId;
    private List<GenreScoreDTO> recommendedGenres;

    public UserGenreRecommendationDTO(Long userId, List<GenreScoreDTO> recommendedGenres) {
        this.userId = userId;
        this.recommendedGenres = recommendedGenres;
    }

    public Long getUserId() { return userId; }
    public List<GenreScoreDTO> getRecommendedGenres() { return recommendedGenres; }
}