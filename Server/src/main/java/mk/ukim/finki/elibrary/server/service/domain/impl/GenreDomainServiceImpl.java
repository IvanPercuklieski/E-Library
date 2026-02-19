package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.dto.update.UpdateGenreDto;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreNotFoundException;
import mk.ukim.finki.elibrary.server.repository.GenreRepository;
import mk.ukim.finki.elibrary.server.service.domain.GenreDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreDomainServiceImpl implements GenreDomainService {

    private final GenreRepository genreRepository;

    public GenreDomainServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(()-> new GenreNotFoundException(id));
    }

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long id, UpdateGenreDto dto) {
        Genre genre=genreRepository.findById(id).orElseThrow(()->new GenreNotFoundException(id));
        genre.setName(dto.title());
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre=genreRepository.findById(id).orElseThrow(()->new GenreNotFoundException(id));
        genreRepository.delete(genre);
    }
}
