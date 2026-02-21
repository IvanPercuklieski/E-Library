package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.repository.AuthorRepository;
import mk.ukim.finki.elibrary.server.service.domain.AuthorDomainService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorDomainServiceImpl implements AuthorDomainService {

    private final AuthorRepository authorRepository;

    public AuthorDomainServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author getAuthorEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public long countBooksByAuthor(Long authorId) {
        Author author = getAuthorEntityById(authorId);
        return author.getBooks() != null ? author.getBooks().size() : 0;
    }


    @Override
    public List<BaseBook> getBooksByAuthor(Long authorId) {
        Author author = getAuthorEntityById(authorId);
        return author.getBooks() != null ? author.getBooks() : new ArrayList<>();
    }
}
