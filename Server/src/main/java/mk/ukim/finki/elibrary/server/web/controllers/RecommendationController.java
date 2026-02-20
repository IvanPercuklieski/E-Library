package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.UserGenreRecommendationDTO;
import mk.ukim.finki.elibrary.server.service.ml.KnnGenreRecommender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final KnnGenreRecommender recommender;

    public RecommendationController(KnnGenreRecommender recommender) {
        this.recommender = recommender;
    }

    @GetMapping("/user/{userId}")
    public UserGenreRecommendationDTO getRecommendations(@PathVariable Long userId) {
        return recommender.recommendGenresForUser(userId, 5, 5);
    }
}
