package mk.ukim.finki.elibrary.server.dto.create;

import java.time.LocalDate;
import java.util.List;

public record CreateBaseBookDto(
        String title,
        Long authorId,
        List<Long> genreIds,
        LocalDate pubDate,
        String description,
        int numBooks
) {}
