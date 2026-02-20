package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CannotRemoveUserException extends RuntimeException {
    public CannotRemoveUserException(Long userId) {
        super(String.format("User with id= %d cannot be removed, they have not returned their books yet or have booked a seat", userId));
    }
}
