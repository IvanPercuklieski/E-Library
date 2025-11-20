package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.BookRecommendationDTO;
import mk.ukim.finki.elibrary.server.dto.BookSimpleDTO;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Primary
public class EmailNotificationService implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    private final JavaMailSender mailSender;
    private final UserWrapperRepository userWrapperRepository;

    public EmailNotificationService(JavaMailSender mailSender,
                                    UserWrapperRepository userWrapperRepository) {
        this.mailSender = mailSender;
        this.userWrapperRepository = userWrapperRepository;
    }

    @Override
    public void sendBookRecommendation(Long userId, BookRecommendationDTO recommendation) {
        UserWrapper user = userWrapperRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            log.warn("Cannot send recommendation email: user {} not found", userId);
            return;
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.warn("Cannot send recommendation email: user {} has no email", userId);
            return;
        }

        String to = user.getEmail();
        String subject = "New book recommendations from your library";

        String body = buildEmailBody(user, recommendation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        log.info("Sent recommendation email to {} ({})", user.getId(), to);
    }

    private String buildEmailBody(UserWrapper user, BookRecommendationDTO rec) {
        if (rec.getRecommendedBooks() == null || rec.getRecommendedBooks().isEmpty()) {
            return "Hello " + user.getName() + ",\n\n" +
                    "Right now we don’t have fresh recommendations that match your profile. " +
                    "Borrow a few more books and we’ll start suggesting new titles just for you. 😊\n\n" +
                    "Your library";
        }

        String booksList = rec.getRecommendedBooks().stream()
                .map(this::formatBookLine)
                .collect(Collectors.joining("\n"));

        return "Hello " + user.getName() + ",\n\n" +
                "Based on your reading history, here  is a book we think you’ll enjoy:\n\n" +
                booksList + "\n\n" +
                "Happy reading!\n" +
                "Your library 📖";
    }
    private String formatBookLine(BookSimpleDTO b) {
        String genres = (b.getGenres() == null || b.getGenres().isEmpty())
                ? ""
                : " (" + String.join(", ", b.getGenres()) + ")";

        return "- " + b.getTitle() + " by " + b.getAuthor() + genres;
    }
}
