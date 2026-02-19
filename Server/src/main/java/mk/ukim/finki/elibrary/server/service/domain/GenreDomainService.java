package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.dto.update.UpdateGenreDto;
import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.util.List;

public interface GenreDomainService {
    List<Genre> getAllGenres();
    Genre getGenreById(Long id);
    Genre createGenre(Genre genre);
    Genre updateGenre(Long genreId, UpdateGenreDto dto);
    void deleteGenre(Long genreId);
}
