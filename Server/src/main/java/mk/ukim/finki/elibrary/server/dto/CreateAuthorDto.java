package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.Author;

public record CreateAuthorDto(String name) {

    public Author toAuthor() {
        return new Author(name);
    }

}
