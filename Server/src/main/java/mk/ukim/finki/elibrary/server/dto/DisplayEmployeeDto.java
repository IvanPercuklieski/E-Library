package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.Employee;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayEmployeeDto(Long id,
                                 Long userId,
                                 String username,
                                 String role) {


    public static DisplayEmployeeDto from(Employee employee) {
        return new DisplayEmployeeDto(
                employee.getId(),
                employee.getUser().getId(),
                employee.getUsername(),
                employee.getRole().name()
        );
    }

    public static List<DisplayEmployeeDto> from(List<Employee> employees) {
        return employees.stream()
                .map(DisplayEmployeeDto::from)
                .collect(Collectors.toList());
    }
}
