package mk.ukim.finki.elibrary.server.service.backend.application;
import mk.ukim.finki.elibrary.server.dto.CreateAuthorDto;
import mk.ukim.finki.elibrary.server.dto.DisplayAuthorDto;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import java.util.List;

public interface AuthorApplicationService {
    List<DisplayAuthorDto> getAllAuthors();
    DisplayAuthorDto getAuthor(Long id);
    DisplayAuthorDto createAuthor(CreateAuthorDto author);
    void removeAuthor(Long id);
}
