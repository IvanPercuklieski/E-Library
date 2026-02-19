package mk.ukim.finki.elibrary.server.dto.display;

import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayBookBaseDto(Long id, String title,
                                 String authorName,
                                 List<String> genreNames,
                                 LocalDate pubDate,
                                 String description,
                                 int numBooks) {


    public static DisplayBookBaseDto from(BaseBook book){
        return new DisplayBookBaseDto(book.getId(),book.getTitle(),book.getAuthor().getName(), book.getGenres() != null
                ? book.getGenres().stream()
                .map(Genre::getName)
                .toList()
                : List.of(), book.getPubDate(),book.getDescription(),book.getNumBooks());
    }

    public static List<DisplayBookBaseDto> from (List<BaseBook> books){
        return books.stream().map(DisplayBookBaseDto::from).collect(Collectors.toList());
    }
    public BaseBook toBaseBook(Author author, List<Genre> genres) {
        return new BaseBook(title, author, genres, pubDate, description, numBooks);
    }

}
