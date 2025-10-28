package mk.ukim.finki.elibrary.server.dto;
import java.time.LocalDate;
import java.util.List;

import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import java.util.stream.Collectors;

public record CreateBaseBookDto(String title, Long authorId, List<Long> genreIds, LocalDate pubDate,
                                String description,
                                int numBooks) {



    public static CreateBaseBookDto from(BaseBook book){
        return new CreateBaseBookDto(book.getTitle(),book.getAuthor().getId(), book.getGenres() != null
                ? book.getGenres().stream()
                .map(Genre::getId)
                .toList()
                : List.of(), book.getPubDate(),book.getDescription(),book.getNumBooks());
    }

    public static List<CreateBaseBookDto> from(List<BaseBook> books){
        return books.stream().map(CreateBaseBookDto::from).collect(Collectors.toList());
    }

    public BaseBook toBookBase(Author author, List<Genre> genres){
        return new BaseBook(title, author, genres, pubDate, description, numBooks);
    }
}
