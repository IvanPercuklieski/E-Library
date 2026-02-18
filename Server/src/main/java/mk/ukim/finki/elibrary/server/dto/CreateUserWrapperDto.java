package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.time.LocalDate;

public record CreateUserWrapperDto(String name,
                                   String surname,
                                   LocalDate fromDate,
                                   LocalDate dueDate,
                                   boolean isMember) {


    public UserWrapper toUserWrapper() {
        UserWrapper user = new UserWrapper();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setFromDate(this.fromDate);
        user.setDueDate(this.dueDate);
        user.setMember(this.isMember);
        return user;
    }
}
