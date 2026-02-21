package mk.ukim.finki.elibrary.server.service.backend.application;
import mk.ukim.finki.elibrary.server.dto.CreateAuthorDto;
import mk.ukim.finki.elibrary.server.dto.DisplayAuthorDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBaseDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateAuthorDto;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import java.util.List;

public interface AuthorApplicationService {
    List<DisplayAuthorDto> getAllAuthors();
    DisplayAuthorDto getAuthor(Long id);
    DisplayAuthorDto createAuthor(CreateAuthorDto author);
    void removeAuthor(Long id);
    DisplayAuthorDto updateAuthor(Long id, UpdateAuthorDto dto);
    long countBooksByAuthor(Long authorId);
    List<DisplayBookBaseDto> getBooksByAuthor(Long authorId);
}
