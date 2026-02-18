package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private boolean isMember;

    @OneToMany(mappedBy = "user")
    private List<BorrowedBook> borrowedBooks;

    private String email;

    public UserWrapper(Long id, String name, String surname, LocalDate fromDate, LocalDate dueDate, boolean isMember,String email ,List<BorrowedBook> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.isMember = isMember;
        this.borrowedBooks = borrowedBooks;
        this.email=email;
    }

    public UserWrapper(String name, String surname, LocalDate fromDate, LocalDate dueDate, boolean isMember, String email) {
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.isMember = isMember;
        this.email=email;

    }
}

