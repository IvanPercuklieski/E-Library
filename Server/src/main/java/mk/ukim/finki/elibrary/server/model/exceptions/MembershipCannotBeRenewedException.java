package mk.ukim.finki.elibrary.server.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class MembershipCannotBeRenewedException extends RuntimeException {
    public MembershipCannotBeRenewedException(String name, String lastname) {
        super(String.format("Membership cannot be renewed for user %s %s. The membership is already ACTIVE", name, lastname));
    }
}
