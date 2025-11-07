package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(Long id) {
        super(String.format("Seat %s is already taken", id));
    }
}
