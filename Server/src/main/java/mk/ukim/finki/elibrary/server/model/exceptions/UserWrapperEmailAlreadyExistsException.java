package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserWrapperEmailAlreadyExistsException extends RuntimeException {
    public UserWrapperEmailAlreadyExistsException(String email) {
        super("User with email " + email + " already exists");
    }
}
