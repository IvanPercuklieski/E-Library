package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_wrapper")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column
    private boolean zachlenet;

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowedBook> borrowedBooks;

    @Column
    private String email;

    public UserWrapper(String filip, String jovanovski, LocalDate localDate, LocalDate localDate1, boolean b, String mail) {
    }
}

