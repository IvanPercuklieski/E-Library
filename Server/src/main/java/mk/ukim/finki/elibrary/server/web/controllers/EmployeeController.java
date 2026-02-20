package mk.ukim.finki.elibrary.server.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.elibrary.server.dto.DisplayEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.LoginEmployeeDto;
import mk.ukim.finki.elibrary.server.dto.RegisterEmployeeDto;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.service.backend.application.EmployeeApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Employee API", description = "Endpoints for managing employees.")
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeApplicationService employeeApplicationService;

    public EmployeeController(EmployeeApplicationService employeeApplicationService) {
        this.employeeApplicationService = employeeApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterEmployeeDto registerEmployeeDto) {
        try {
            return ResponseEntity.ok(employeeApplicationService.register(registerEmployeeDto));
        } catch (EmployeeCanBeRegisteredException | PasswordsDoNotMatchException |
                 UsernameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @Operation(summary = "Employee login", description = "Authenticates an employee and generates a JWT")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "Employee authenticated successfully"
            ), @ApiResponse(responseCode = "404", description = "Invalid username or password")}
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginEmployeeDto loginEmployeeDto) {
        try {
            return ResponseEntity.ok(employeeApplicationService.login(loginEmployeeDto));
        } catch (EmployeeDoesntExistException | InvalidUserCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Employee logout", description = "Ends the employee's session")
    @ApiResponse(responseCode = "200", description = "Employee logged out successfully")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all employees", description = "Get all employees from the system")
    public List<DisplayEmployeeDto> getAllEmployees() {
        return employeeApplicationService.getAllEmployees();
    }

}
