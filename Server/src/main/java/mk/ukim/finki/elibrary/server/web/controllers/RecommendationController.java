package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.UserGenreRecommendationDTO;
import mk.ukim.finki.elibrary.server.service.ml.KnnGenreRecommender;
import org.springframework.web.bind.annotation.*;

//Kontolerot sluzhi samo za testiranje na reccomendation datata
//(ne koristi servisi, direktno e povrzan so ml-kodot)

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
