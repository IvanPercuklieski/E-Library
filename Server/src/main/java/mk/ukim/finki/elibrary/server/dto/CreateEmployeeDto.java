package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import mk.ukim.finki.elibrary.server.model.domain.Employee;

public record CreateEmployeeDto(Long userId,
                                String username,
                                String password,
                                String email,
                                EmployeeType role) {

    public Employee toEmployee(UserWrapper user) {
        return new Employee(user, username, password, email, role);
    }


}
