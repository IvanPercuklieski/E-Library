package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.time.LocalDate;

public record CreateUserWrapperDto(String name,
                                   String surname,
                                   LocalDate from,
                                   LocalDate dueDate,
                                   boolean zachlenet) {


    public UserWrapper toUserWrapper() {
        UserWrapper user = new UserWrapper();
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setFrom(this.from);
        user.setDueDate(this.dueDate);
        user.setZachlenet(this.zachlenet);
        return user;
    }
}
