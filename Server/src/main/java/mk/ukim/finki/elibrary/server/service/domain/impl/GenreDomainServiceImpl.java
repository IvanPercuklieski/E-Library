package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.Genre;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreAlreadyExistsException;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreNameNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.GenreNotFoundException;
import mk.ukim.finki.elibrary.server.repository.BaseBookRepository;
import mk.ukim.finki.elibrary.server.repository.GenreRepository;
import mk.ukim.finki.elibrary.server.service.domain.GenreDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreDomainServiceImpl implements GenreDomainService {

    private final GenreRepository genreRepository;
    private final BaseBookRepository baseBookRepository;

    public GenreDomainServiceImpl(GenreRepository genreRepository, BaseBookRepository baseBookRepository) {
        this.genreRepository = genreRepository;
        this.baseBookRepository = baseBookRepository;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> addGenre(Genre genre) {

        String genreNameUpperCase= genre.getName().trim().toUpperCase();

        if (genreRepository.findByName(genreNameUpperCase).isPresent()){
            throw new GenreAlreadyExistsException(genreNameUpperCase);
        }

        return Optional.of(genreRepository.save(genre));
    }

    @Override
    public Optional<Genre> deleteGenreById(Long genreId) {

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));

        List<BaseBook> associatedBooks = getAssociatedBooks(genre);

        // removes the genre from each book's list (clears the Join Table entries)
        for (BaseBook book : associatedBooks) {
            book.getGenres().remove(genre);
        }

        baseBookRepository.saveAll(associatedBooks);

        genreRepository.delete(genre);

        return Optional.of(genre);
    }

    @Override
    public Optional<Genre> deleteGenreByName(String genreName) {

        String genreNameUpperCase= genreName.trim().toUpperCase();

        Genre genre = genreRepository.findByName(genreNameUpperCase)
                .orElseThrow(() -> new GenreNameNotFoundException(genreNameUpperCase));

        List<BaseBook> associatedBooks = getAssociatedBooks(genre);

        for (BaseBook book : associatedBooks) {
            book.getGenres().remove(genre);
        }

        baseBookRepository.saveAll(associatedBooks);

        genreRepository.delete(genre);

        return Optional.of(genre);
    }

    private List<BaseBook> getAssociatedBooks(Genre genre) {
        return baseBookRepository.findByGenresContains(genre);
    }
}
