package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserWrapperNotFoundException extends RuntimeException {
    public UserWrapperNotFoundException(Long id) {
        super(String.format("User wrapper with id %s not found", id));
    }
}
