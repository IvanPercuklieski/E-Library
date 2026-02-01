package mk.ukim.finki.elibrary.server.model.domain;
import lombok.NoArgsConstructor;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@NoArgsConstructor
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
}
