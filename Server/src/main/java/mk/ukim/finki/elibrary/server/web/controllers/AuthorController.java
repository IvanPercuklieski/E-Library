package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.CreateAuthorDto;
import mk.ukim.finki.elibrary.server.dto.DisplayAuthorDto;
import mk.ukim.finki.elibrary.server.service.backend.application.AuthorApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {


        private final AuthorApplicationService authorAppService;

        public AuthorController(AuthorApplicationService authorAppService){
            this.authorAppService = authorAppService;
        }


    @GetMapping
    public List<DisplayAuthorDto> getAllAuthors() {
        return authorAppService.getAllAuthors();
    }


    @GetMapping("/{id}")
    public DisplayAuthorDto getAuthor(@PathVariable Long id) {
        return authorAppService.getAuthor(id);
    }


    @PostMapping
    public DisplayAuthorDto createAuthor(@RequestBody(required = false) CreateAuthorDto author) {
        if (author == null) {
            throw new IllegalArgumentException("Author data is required");
        }
        return authorAppService.createAuthor(author);
    }


    @DeleteMapping("/{id}")
    public void removeAuthor(@PathVariable Long id) {
        authorAppService.removeAuthor(id);
    }
}


