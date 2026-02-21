package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBaseDto;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;

import java.util.List;

public interface AuthorDomainService {

    List<Author> getAllAuthors();
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    Author getAuthorEntityById(Long id);
    long countBooksByAuthor(Long authorId);
    List<BaseBook> getBooksByAuthor(Long authorId);
}
