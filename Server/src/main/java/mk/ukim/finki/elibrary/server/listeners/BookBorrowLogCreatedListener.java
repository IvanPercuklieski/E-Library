package mk.ukim.finki.elibrary.server.listeners;

import mk.ukim.finki.elibrary.server.dto.BookRecommendationDTO;
import mk.ukim.finki.elibrary.server.events.BookBorrowLogCreatedEvent;
import mk.ukim.finki.elibrary.server.service.backend.application.NotificationService;
import mk.ukim.finki.elibrary.server.service.backend.application.impl.BookRecommendationServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class BookBorrowLogCreatedListener {

    private final BookRecommendationServiceImpl bookRecommendationService;
    private final NotificationService notificationService;
    private static final Logger log = LoggerFactory.getLogger(BookBorrowLogCreatedListener.class);
    public BookBorrowLogCreatedListener(BookRecommendationServiceImpl bookRecommendationService,
                                        NotificationService notificationService) {
        this.bookRecommendationService = bookRecommendationService;
        this.notificationService = notificationService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onBookBorrowLogCreated(BookBorrowLogCreatedEvent event) {
        try{
            Long userId = event.getUserId();

            BookRecommendationDTO rec =
                    bookRecommendationService.recommendSingleBookForUser(userId);

            notificationService.sendBookRecommendation(userId, rec);
        }
        catch(Exception ex){
            log.error("Failed to send recommendation for user {}", event.getUserId(), ex);
        }

    }
}

