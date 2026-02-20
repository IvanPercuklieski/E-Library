package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.Employee;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.repository.EmployeeRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.EmployeeService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserWrapperRepository userWrapperRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserWrapperRepository userWrapperRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee register(String username, String password, String repeatPassword, String email) {
       // 1. provrka dali adminot go dodal noviot employee vo userwrappper,
        // predavame email i gledame dali ima takov user so takov mail vo baza
        // ako da si kreira employee account
        UserWrapper userWrapper = userWrapperRepository.findUserWrapperByEmail(email).orElseThrow(()-> new EmployeeCanBeRegisteredException(email));

        if (!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();

        if (employeeRepository.findEmployeeByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);


        Employee employeeAccount = new Employee(userWrapper, username, passwordEncoder.encode(password), EmployeeType.BASIC);
        return employeeRepository.save(employeeAccount);

    }


    @Override
    public Employee login(String username, String password) {
        Employee employee = employeeRepository.findEmployeeByUsername(username).orElseThrow(()-> new EmployeeDoesntExistException(username));

        if (!passwordEncoder.matches(password, employee.getPassword())) throw new InvalidUserCredentialsException();

        return employee;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> findEmployeeByRole(EmployeeType role) {
        return employeeRepository.findEmployeeByRole(role).or(Optional::empty);
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return Optional.of(employeeRepository.findById(id).orElseThrow(() -> new EmployeeIdDoesntExistException(id)));
    }

    @Override
    public Optional<Employee> findEmployeeByUsername(String username) {
        return Optional.of(employeeRepository.findEmployeeByUsername(username).orElseThrow(() -> new EmployeeDoesntExistException(username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findEmployeeByUsername(username).orElseThrow(() -> new EmployeeDoesntExistException(username));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
