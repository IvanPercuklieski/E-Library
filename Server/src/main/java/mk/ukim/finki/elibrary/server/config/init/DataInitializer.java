package mk.ukim.finki.elibrary.server.config.init;
import jakarta.annotation.PostConstruct;
import mk.ukim.finki.elibrary.server.model.domain.*;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import mk.ukim.finki.elibrary.server.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BaseBookRepository baseBookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final UserWrapperRepository userWrapperRepository;
    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final BorrowedBookLogRepository bookBorrowLogRepository;
    private final EmployeeRepository employeeRepository;

    public DataInitializer(
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BaseBookRepository baseBookRepository,
            BookCopyRepository bookCopyRepository,
            UserWrapperRepository userWrapperRepository,
            RoomRepository roomRepository,
            SeatRepository seatRepository,
            BorrowedBookRepository borrowedBookRepository,
            BorrowedBookLogRepository bookBorrowLogRepository,
            EmployeeRepository employeeRepository

    ) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.baseBookRepository = baseBookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.userWrapperRepository = userWrapperRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookBorrowLogRepository = bookBorrowLogRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void init() {

        Genre fantasy = new Genre("Fantazija");
        Genre history = new Genre("Istorija");
        Genre sciFi = new Genre("Naucna fantastika");
        genreRepository.saveAll(List.of(fantasy, history, sciFi));


        Author sheti = new Author("Dzej Sheti");
        Author vist = new Author("Brijana Vist");
        Author bruer = new Author("Dzadson Bruer");
        authorRepository.saveAll(List.of(sheti,vist,bruer));


        UserWrapper user1 = new UserWrapper("Milena", "Milenovska", LocalDate.now(), LocalDate.now().plusDays(14), true);
        UserWrapper user2 = new UserWrapper("Marko", "Markovski", LocalDate.now(), LocalDate.now().plusDays(7), false);
        userWrapperRepository.saveAll(List.of(user1, user2));


        BaseBook book1 = new BaseBook("Osum pravila na ljubovta", sheti, List.of(fantasy), LocalDate.of(1937, 9, 21), "Klasnicna naucna fantastika", 5);
        BaseBook book2 = new BaseBook("Vlijanie", vist, List.of(sciFi), LocalDate.of(1951, 1, 1), "Istorija ", 3);
        BaseBook book3 = new BaseBook("Staklenoto dzvono", bruer, List.of(history), LocalDate.of(2011, 1, 1), "Fantazija-Istorija", 4);
        baseBookRepository.saveAll(List.of(book1, book2, book3));


        BookCopy copy1 = new BookCopy(book1);
        BookCopy copy2 = new BookCopy(book2);
        BookCopy copy3 = new BookCopy(book3);
        bookCopyRepository.saveAll(List.of(copy1, copy2, copy3));


        Room room1 = new Room("Sala 1", "Prv kat", 10, null);
        Room room2 = new Room("Sala 3", "Vtor kat", 8, null);
        roomRepository.saveAll(List.of(room1, room2));


        Seat seat1 = new Seat(1, false, user1, room1);
        Seat seat2 = new Seat(2, true, user2, room2);
        seatRepository.saveAll(List.of(seat1, seat2));

        Employee employee1 = new Employee(user1, "admin", "admin123", "admin@mail.com", EmployeeType.ADMIN);
        Employee employee2 = new Employee(user2, "obichen", "ob123", "obichen@mail.com", EmployeeType.OBICHEN);

        employeeRepository.saveAll(List.of(employee1, employee2));

        BorrowedBook borrowed1 = new BorrowedBook(null, LocalDateTime.now(), LocalDateTime.now().plusDays(10), user1, copy1);
        borrowedBookRepository.save(borrowed1);


        BookBorrowLog log1 = new BookBorrowLog(
                LocalDateTime.now(),
                null,
                LocalDateTime.now().plusDays(10),
                "Borrowed normally",
                copy1,
                user1
        );
        bookBorrowLogRepository.save(log1);
    }
}
