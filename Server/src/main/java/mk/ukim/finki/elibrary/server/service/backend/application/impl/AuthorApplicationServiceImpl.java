package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateAuthorDto;
import mk.ukim.finki.elibrary.server.dto.DisplayAuthorDto;
import mk.ukim.finki.elibrary.server.model.domain.Author;
import mk.ukim.finki.elibrary.server.service.backend.application.AuthorApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.AuthorDomainService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {


    private final AuthorDomainService authorDomainService;

    public AuthorApplicationServiceImpl(AuthorDomainService authorDomainService) {
        this.authorDomainService = authorDomainService;
    }

    @Override
    public List<DisplayAuthorDto> getAllAuthors() {
        List<Author> authors = authorDomainService.getAllAuthors();
        return DisplayAuthorDto.from(authors);
    }


    @Override
    public void removeAuthor(Long id) {
        authorDomainService.deleteAuthor(id);
    }


    @Override
    public DisplayAuthorDto getAuthor(Long id) {
        Author author = authorDomainService.getAuthorEntityById(id);
        return DisplayAuthorDto.from(author);
    }

    @Override
    public DisplayAuthorDto createAuthor(CreateAuthorDto authorDto) {
        Author author = authorDto.toAuthor();
        Author saved = authorDomainService.saveAuthor(author);
        return DisplayAuthorDto.from(saved);
    }
}
