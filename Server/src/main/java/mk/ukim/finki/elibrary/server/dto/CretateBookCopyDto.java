package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

public record CretateBookCopyDto(Long baseBookId) {

    public BookCopy toBookCopy(BaseBook baseBook) {
        return new BookCopy(baseBook);
    }
}
