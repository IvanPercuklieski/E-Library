package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateGenreDto;
import mk.ukim.finki.elibrary.server.dto.DisplayGenreDto;
import mk.ukim.finki.elibrary.server.service.backend.application.GenreApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.GenreDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GenreApplicationServiceImpl implements GenreApplicationService {

    private final GenreDomainService genreDomainService;

    public GenreApplicationServiceImpl(GenreDomainService genreDomainService) {
        this.genreDomainService = genreDomainService;
    }

    @Override
    public List<DisplayGenreDto> getAllGenres() {
        return genreDomainService.getAllGenres()
                .stream().map(DisplayGenreDto::from).toList();
    }

    @Override
    public Optional<DisplayGenreDto> addGenre(CreateGenreDto genreDto) {
        return genreDomainService.addGenre(genreDto.toGenre())
                .map(DisplayGenreDto::from);
    }

    @Override
    public Optional<DisplayGenreDto> deleteGenreById(Long genreId) {
        return genreDomainService.deleteGenreById(genreId).map(DisplayGenreDto::from);
    }

    @Override
    public Optional<DisplayGenreDto> deleteGenreByName(String genreName) {
        return genreDomainService.deleteGenreByName(genreName.trim().toUpperCase())
                .map(DisplayGenreDto::from);
    }
}
