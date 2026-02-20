package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.domain.Employee;

public record RegisterEmployeeDto(Long userId,
                                  String username,
                                  String password,
                                  String repeatPassword,
                                  String email) {

    public Employee toEmployee(UserWrapper user) {
        return new Employee(user, username, password);
    }
}
