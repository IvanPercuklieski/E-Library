package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.CreateAuthorDto;
import mk.ukim.finki.elibrary.server.dto.DisplayAuthorDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBaseDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateAuthorDto;
import mk.ukim.finki.elibrary.server.service.backend.application.AuthorApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create-author")
    public DisplayAuthorDto createAuthor(@RequestBody(required = false) CreateAuthorDto author) {
        if (author == null) {
            throw new IllegalArgumentException("Author data is required");
        }
        return authorAppService.createAuthor(author);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public DisplayAuthorDto updateAuthor(@PathVariable Long id,
                                         @RequestBody UpdateAuthorDto dto) {
        return authorAppService.updateAuthor(id, dto);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public void removeAuthor(@PathVariable Long id) {
        authorAppService.removeAuthor(id);
    }

    @GetMapping("/{id}/books/count")
    public long countBooks(@PathVariable Long id) {
        return authorAppService.countBooksByAuthor(id);
    }

    @GetMapping("/{id}/books")
    public List<DisplayBookBaseDto> getBooks(@PathVariable Long id) {
        return authorAppService.getBooksByAuthor(id);
    }
}


