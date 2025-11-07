package mk.ukim.finki.elibrary.server.model.domain;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private EmployeeType role;

    public Employee(Long id, UserWrapper user, String username, String password, String email, EmployeeType role) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Employee(UserWrapper user, String username, String password, String email, EmployeeType role) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Employee() {

    }

    public Long getId() {
        return id;
    }

    public UserWrapper getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeType getRole() {
        return role;
    }

    public void setUser(UserWrapper user) {
        this.user = user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(EmployeeType role) {
        this.role = role;
    }
}
