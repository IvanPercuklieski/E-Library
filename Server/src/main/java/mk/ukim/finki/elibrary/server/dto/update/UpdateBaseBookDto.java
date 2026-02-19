package mk.ukim.finki.elibrary.server.dto.update;

import java.time.LocalDate;
import java.util.List;

public record UpdateBaseBookDto(
        Long id,
        String title,
        Long authorId,
        List<Long> genreIds,
        LocalDate pubDate,
        String description,

        int requestedTotalCopies
) {}
