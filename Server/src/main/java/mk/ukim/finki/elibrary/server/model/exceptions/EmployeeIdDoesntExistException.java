package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeIdDoesntExistException extends RuntimeException {
    public EmployeeIdDoesntExistException(Long id) {
        super(String.format("Employee with id= %d doesn't exist", id));
    }
}
