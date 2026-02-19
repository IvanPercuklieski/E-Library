package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.create.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayGenreDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateGenreDto;
import mk.ukim.finki.elibrary.server.model.domain.Genre;

import java.util.List;

public interface GenreApplicationService {
    List<DisplayGenreDto> getAllGenres();
    DisplayGenreDto getGenreById(Long id);
    DisplayGenreDto createGenre(CreateGenreDto genre);
    DisplayGenreDto updateGenre(Long genreId, UpdateGenreDto dto);
    void deleteGenre(Long genreId);

}
