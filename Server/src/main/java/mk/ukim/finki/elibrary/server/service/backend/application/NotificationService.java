package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.BookRecommendationDTO;

public interface NotificationService {

    void sendBookRecommendation(Long userId, BookRecommendationDTO rec);

}
