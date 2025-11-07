package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(Long id) {
        super(String.format("Seat with id %s not found", id));
    }
}
