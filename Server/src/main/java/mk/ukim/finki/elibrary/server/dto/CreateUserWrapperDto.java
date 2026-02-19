package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

public record CreateUserWrapperDto(String name,
                                   String surname,
                                   String email){


    public UserWrapper toUserWrapper() {
        UserWrapper user = new UserWrapper();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        return user;
    }
}
