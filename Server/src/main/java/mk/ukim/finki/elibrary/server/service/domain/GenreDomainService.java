package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDomainService {

    List<Genre> getAllGenres();
    Optional<Genre> addGenre(Genre genre);
    Optional<Genre> deleteGenreById(Long genreId);
    Optional<Genre> deleteGenreByName(String genreName);
}
