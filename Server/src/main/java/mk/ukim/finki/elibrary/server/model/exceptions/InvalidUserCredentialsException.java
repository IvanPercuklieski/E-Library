package mk.ukim.finki.elibrary.server.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid credentials.");
    }
}
