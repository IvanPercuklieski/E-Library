package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(Long id) {
        super(String.format("Book with id %d is already borrowed", id));
    }
}
