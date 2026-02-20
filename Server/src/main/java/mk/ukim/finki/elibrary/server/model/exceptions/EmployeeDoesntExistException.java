package mk.ukim.finki.elibrary.server.model.exceptions;

public class EmployeeDoesntExistException extends RuntimeException {
    public EmployeeDoesntExistException(String username) {
        super(String.format("Employee with username= %s doesn't exist", username));
    }
}
