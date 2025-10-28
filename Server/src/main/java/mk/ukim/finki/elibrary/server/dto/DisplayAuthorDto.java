package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayAuthorDto(Long id, String name, List<String> bookTitles) {


    public static DisplayAuthorDto from(Author author){
        return new DisplayAuthorDto(author.getId(), author.getName(),  author.getBooks().stream()
                .map(BaseBook::getTitle).toList());
    }

    public static List<DisplayAuthorDto> from(List<Author> authors) {
        return authors.stream().map(DisplayAuthorDto::from).collect(Collectors.toList());
    }


}
