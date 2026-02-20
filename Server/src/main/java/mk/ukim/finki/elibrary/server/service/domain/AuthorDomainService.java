package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import java.util.List;

public interface AuthorDomainService {

    List<Author> getAllAuthors();
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    Author getAuthorEntityById(Long id);
}
