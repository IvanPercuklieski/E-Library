package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.DisplayGenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreApplicationService {
    List<DisplayGenreDto> getAllGenres();
    Optional<DisplayGenreDto> addGenre(CreateGenreDto genreDto);
    Optional<DisplayGenreDto> deleteGenreById(Long genreId);
    Optional<DisplayGenreDto> deleteGenreByName(String genreName);
}
