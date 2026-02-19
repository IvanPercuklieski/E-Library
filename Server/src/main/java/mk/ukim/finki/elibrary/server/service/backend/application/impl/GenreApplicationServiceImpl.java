package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.create.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayGenreDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateGenreDto;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import mk.ukim.finki.elibrary.server.service.backend.application.GenreApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.GenreDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreApplicationServiceImpl implements GenreApplicationService {

    private final GenreDomainService genreDomainService;

    public GenreApplicationServiceImpl(GenreDomainService genreDomainService) {
        this.genreDomainService = genreDomainService;
    }

    @Override
    public List<DisplayGenreDto> getAllGenres() {
        return DisplayGenreDto.from(genreDomainService.getAllGenres());
    }

    @Override
    public DisplayGenreDto getGenreById(Long id) {
        return DisplayGenreDto.from(genreDomainService.getGenreById(id));
    }

    @Override
    public DisplayGenreDto createGenre(CreateGenreDto genre) {
        Genre genre1=new Genre();
        genre1.setName(genre.title());
        return DisplayGenreDto.from(genreDomainService.createGenre(genre1));
    }

    @Override
    public DisplayGenreDto updateGenre(Long genreId, UpdateGenreDto dto) {
       return DisplayGenreDto.from(genreDomainService.updateGenre(genreId,dto));

    }

    @Override
    public void deleteGenre(Long genreId) {
    genreDomainService.deleteGenre(genreId);
    }
}
