package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_wrapper")
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

    private boolean zachlenet;

    @OneToMany(mappedBy = "user")
    private List<BorrowedBook> borrowedBooks;

    private String email;

    public UserWrapper(Long id, String name, String surname, LocalDate fromDate, LocalDate dueDate, boolean zachlenet,String email ,List<BorrowedBook> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.zachlenet = zachlenet;
        this.borrowedBooks = borrowedBooks;
        this.email=email;
    }

    public UserWrapper(String name, String surname, LocalDate fromDate, LocalDate dueDate, boolean zachlenet, String email) {
        this.name = name;
        this.surname = surname;
        this.fromDate = fromDate;
        this.dueDate = dueDate;
        this.zachlenet = zachlenet;
        this.email=email;

    }

    public UserWrapper() {

    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isZachlenet() {
        return zachlenet;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setZachlenet(boolean zachlenet) {
        this.zachlenet = zachlenet;
    }

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}

