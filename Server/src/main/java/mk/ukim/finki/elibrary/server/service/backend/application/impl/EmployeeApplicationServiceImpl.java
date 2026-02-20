package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.DisplayEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.EmployeeLoginResponseDto;
import mk.ukim.finki.elibrary.server.dto.LoginEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.RegisterEmployeeDto;
import mk.ukim.finki.elibrary.server.helpers.JwtHelper;
import mk.ukim.finki.elibrary.server.model.domain.Employee;
import mk.ukim.finki.elibrary.server.service.backend.application.EmployeeApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeApplicationServiceImpl implements EmployeeApplicationService {

    private final EmployeeService employeeService;
    private final JwtHelper jwtHelper;

    public EmployeeApplicationServiceImpl(EmployeeService employeeService, JwtHelper jwtHelper) {
        this.employeeService = employeeService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<DisplayEmployeeDto> register(RegisterEmployeeDto createUserDto) {
        Employee employee = employeeService.register(createUserDto.username(), createUserDto.password(), createUserDto.repeatPassword(), createUserDto.email());
        return Optional.of(DisplayEmployeeDto.from(employee));
    }

    @Override
    public Optional<EmployeeLoginResponseDto> login(LoginEmployeeDto loginUserDto) {
       Employee employee = employeeService.login(loginUserDto.username(), loginUserDto.password());

        String token = jwtHelper.generateToken(employee);

        return Optional.of(new EmployeeLoginResponseDto(token));
    }

    @Override
    public Optional<DisplayEmployeeDto> findByUsername(String username) {
        return employeeService.findEmployeeByUsername(username)
                .map(DisplayEmployeeDto::from);
    }

    @Override
    public List<DisplayEmployeeDto> getAllEmployees() {
        return DisplayEmployeeDto.from(employeeService.getAllEmployees());
    }
}
