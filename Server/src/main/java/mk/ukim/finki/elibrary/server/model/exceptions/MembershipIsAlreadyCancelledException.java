package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class MembershipIsAlreadyCancelledException extends RuntimeException {
    public MembershipIsAlreadyCancelledException(Long id) {
        super(String.format("The membership with of the user with id= %d is already cancelled", id));
    }
}
