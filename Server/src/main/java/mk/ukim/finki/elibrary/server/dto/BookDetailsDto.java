package mk.ukim.finki.elibrary.server.dto;

import java.time.LocalDate;
import java.util.List;

public record BookDetailsDto(
            Long id,
            String title,
            String authorName,
            List<String> genres,
            LocalDate pubDate,
            String description,
            int availableCopies,
            List<ReviewDisplayDto> reviews
) {}




