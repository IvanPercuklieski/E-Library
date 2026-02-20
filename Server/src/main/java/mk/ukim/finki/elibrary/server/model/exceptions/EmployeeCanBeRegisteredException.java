package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmployeeCanBeRegisteredException extends RuntimeException {
    public EmployeeCanBeRegisteredException(String email) {
        super(String.format("Employee with email= %s has not been added in the UserWrapper by the administrator", email));
    }
}
