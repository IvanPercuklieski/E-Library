package mk.ukim.finki.elibrary.server.model.domain;
import lombok.*;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserWrapper user;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private EmployeeType role;

    public Employee(String username, String password, String email, EmployeeType role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
