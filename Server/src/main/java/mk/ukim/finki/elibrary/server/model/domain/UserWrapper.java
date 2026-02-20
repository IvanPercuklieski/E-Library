package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.elibrary.server.model.enumerations.MembershipStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_wrapper")
@Data
@NoArgsConstructor
public class UserWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Column(columnDefinition = "DATE")
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowedBook> borrowedBooks = new ArrayList<>();

    private String email;

    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;

    public UserWrapper(Long id, String name, String surname, LocalDate fromDate, LocalDate dueDate,String email ,List<BorrowedBook> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.borrowedBooks = borrowedBooks;
        this.email=email;
        this.membershipStatus=MembershipStatus.ACTIVE;
    }

    public UserWrapper(String name, String surname, LocalDate fromDate, LocalDate dueDate, String email) {
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.email=email;
        this.membershipStatus=MembershipStatus.ACTIVE;
    }
}

