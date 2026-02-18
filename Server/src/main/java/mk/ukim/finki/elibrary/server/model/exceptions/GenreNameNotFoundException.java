package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GenreNameNotFoundException extends RuntimeException {
    public GenreNameNotFoundException(String genreName) {
        super(String.format("The gere with name= %s is not found", genreName));
    }
}
