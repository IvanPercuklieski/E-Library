package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.BookRecommendationDTO;
import mk.ukim.finki.elibrary.server.dto.BookSimpleDTO;
import mk.ukim.finki.elibrary.server.dto.GenreScoreDTO;
import mk.ukim.finki.elibrary.server.dto.UserGenreRecommendationDTO;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookBorrowLog;
import mk.ukim.finki.elibrary.server.repository.BaseBookRepository;
import mk.ukim.finki.elibrary.server.repository.BorrowedBookLogRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.BookRecommendationService;
import mk.ukim.finki.elibrary.server.service.ml.KnnGenreRecommender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookRecommendationServiceImpl implements BookRecommendationService {

    private final KnnGenreRecommender recommender;
    private final BaseBookRepository baseBookRepository;
    private final BorrowedBookLogRepository bookBorrowLogRepository;

    public BookRecommendationServiceImpl(KnnGenreRecommender recommender,
                                         BaseBookRepository baseBookRepository,
                                         BorrowedBookLogRepository bookBorrowLogRepository) {
        this.recommender = recommender;
        this.baseBookRepository = baseBookRepository;
        this.bookBorrowLogRepository = bookBorrowLogRepository;
    }

    public BookRecommendationDTO recommendSingleBookForUser(Long userId) {
        UserGenreRecommendationDTO genreRec =
                recommender.recommendGenresForUser(userId, 5, 5);

        List<Long> borrowedBaseBookIds =
                bookBorrowLogRepository.findDistinctBaseBookIdsBorrowedByUser(userId);
        System.out.println(borrowedBaseBookIds);

        for (GenreScoreDTO gs : genreRec.getRecommendedGenres()) {
            Long genreId = gs.getGenreId();

            List<BaseBook> candidates;
            if (borrowedBaseBookIds.isEmpty()) {
                candidates = baseBookRepository.findAvailableByGenre(genreId);
            } else {
                candidates = baseBookRepository.findAvailableByGenreExcludingBorrowed(genreId, borrowedBaseBookIds);
            }

            if (!candidates.isEmpty()) {
                BaseBook chosen = candidates.get(0);
                BookSimpleDTO dto = BookSimpleDTO.fromBaseBook(chosen);
                return new BookRecommendationDTO(userId, List.of(dto));
            }
        }

        List<BookBorrowLog> logs =
                bookBorrowLogRepository.findByUserIdOrderByBorrowedAtAsc(userId);

        if (!logs.isEmpty()) {
            BaseBook oldest = logs.get(0).getBookCopy().getBaseBook();
            BookSimpleDTO dto = BookSimpleDTO.fromBaseBook(oldest);
            return new BookRecommendationDTO(userId, List.of(dto));
        }

        return new BookRecommendationDTO(userId, List.of());
    }
}
