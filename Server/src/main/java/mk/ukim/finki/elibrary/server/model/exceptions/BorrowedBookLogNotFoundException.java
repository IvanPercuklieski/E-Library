package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BorrowedBookLogNotFoundException extends RuntimeException {
    public BorrowedBookLogNotFoundException(Long id) {
        super(String.format("Book log with id %s not found", id));
    }
}
