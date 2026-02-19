package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    void deleteEmployeeByUser_Id(Long userId);
}
