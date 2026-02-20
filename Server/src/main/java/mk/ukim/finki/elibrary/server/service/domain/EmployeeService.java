package mk.ukim.finki.elibrary.server.service.domain;

import mk.ukim.finki.elibrary.server.model.domain.Employee;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface EmployeeService extends UserDetailsService {

    // register
    Employee register(String username, String password, String repeatPassword, String email);

    // login
    Employee login(String username, String password);
    // logout

    // employee details
    Optional<Employee> getEmployeeById(Long id);

    // find employee by role
    Optional<Employee> findEmployeeByRole(EmployeeType role);

    // find employee by id
    Optional<Employee> findEmployeeById(Long id);

    // find employee by username
    Optional<Employee> findEmployeeByUsername(String username);

    List<Employee> getAllEmployees();

}
