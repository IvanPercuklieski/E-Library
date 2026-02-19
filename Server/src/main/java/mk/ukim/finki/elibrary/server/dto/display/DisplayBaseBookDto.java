package mk.ukim.finki.elibrary.server.dto.display;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public record DisplayBaseBookDto(
        Long id,
        String title,
        Long authorId,
        String authorName,
        List<Long> genreIds,
        List<String> genreNames,
        LocalDate pubDate,
        String description,

        long totalBookCopies,
        long availableBookCopies,
        long activeBorrowings
) {

    public static DisplayBaseBookDto from(BaseBook book,
                                          long totalBookCopies,
                                          long availableBookCopies,
                                          long activeBorrowings) {

        return new DisplayBaseBookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getGenres() != null ? book.getGenres().stream().map(Genre::getId).toList() : List.of(),
                book.getGenres() != null ? book.getGenres().stream().map(Genre::getName).toList() : List.of(),
                book.getPubDate(),
                book.getDescription(),
                totalBookCopies,
                availableBookCopies,
                activeBorrowings
        );
    }


    public static List<DisplayBaseBookDto> from(
            List<BaseBook> books,
            Function<Long, Counters> countersProvider
    ) {
        return books.stream()
                .map(b -> {
                    Counters c = countersProvider.apply(b.getId());
                    return DisplayBaseBookDto.from(b, c.totalBookCopies(), c.availableBookCopies(), c.activeBorrowings());
                })
                .toList();
    }

    public record Counters(long totalBookCopies, long availableBookCopies, long activeBorrowings) {}
}
