package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(Long id) {
        super(String.format("Book copy with id %d not found", id));
    }
}
