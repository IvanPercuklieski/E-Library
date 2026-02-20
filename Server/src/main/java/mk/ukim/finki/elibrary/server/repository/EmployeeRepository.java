package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.Employee;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByUsername(String username);

    Optional<Employee> findEmployeeByRole(EmployeeType role);


    void deleteEmployeeByUser_Id(Long userId);
}
