package mk.ukim.finki.elibrary.server.model.domain;
import lombok.NoArgsConstructor;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private EmployeeType role;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;


    public Employee(Long id, UserWrapper user, String username, String password, EmployeeType role) {
        this.id = id;
        this.user = user;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Employee(UserWrapper user, String username, String password, EmployeeType role) {
        this.user = user;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority( role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
