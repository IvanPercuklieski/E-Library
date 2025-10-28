package mk.ukim.finki.elibrary.server.dto;
import java.util.List;
import java.util.stream.Collectors;

import mk.ukim.finki.elibrary.server.model.domain.Genre;

public record DisplayGenreDto(Long id, String name) {

    public static DisplayGenreDto from(Genre genre) {
        return new DisplayGenreDto(
                genre.getId(),
                genre.getName()
        );
    }


    public static List<DisplayGenreDto> from(List<Genre> genres) {
        return genres.stream()
                .map(DisplayGenreDto::from)
                .collect(Collectors.toList());
    }
}

