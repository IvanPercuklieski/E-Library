package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class GenreAlreadyExistsException extends RuntimeException {
    public GenreAlreadyExistsException(String name) {
        super(String.format("Genre with name %s already exists", name));
    }
}
