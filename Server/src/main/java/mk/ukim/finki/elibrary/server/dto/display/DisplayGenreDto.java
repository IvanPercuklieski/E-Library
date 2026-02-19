package mk.ukim.finki.elibrary.server.dto.display;

import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.util.List;

public record DisplayGenreDto(
        Long id,
        String name
) {
    public static DisplayGenreDto from(Genre genre){
     return new DisplayGenreDto(genre.getId(), genre.getName());
    }

    public static List<DisplayGenreDto> from(List<Genre> genres){
        return genres.stream().map(DisplayGenreDto::from).toList() ;
    }
}
