package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.Genre;

public record CreateGenreDto(String name) {

    public Genre toGenre() {
        return new Genre(name);
    }
}
