package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BorrowedBookNotFoundException extends RuntimeException {
    public BorrowedBookNotFoundException(Long id) {
        super(String.format("Borrowed book with id %s not found", id));
    }
}
