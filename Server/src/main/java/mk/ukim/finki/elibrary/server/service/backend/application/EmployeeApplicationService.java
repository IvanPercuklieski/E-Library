package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.EmployeeLoginResponseDto;
import mk.ukim.finki.elibrary.server.dto.LoginEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.RegisterEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.DisplayEmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeApplicationService {
    Optional<DisplayEmployeeDto> register(RegisterEmployeeDto createUserDto);

    Optional<EmployeeLoginResponseDto> login(LoginEmployeeDto loginUserDto);

    Optional<DisplayEmployeeDto> findByUsername(String username);

    List<DisplayEmployeeDto> getAllEmployees();

}
