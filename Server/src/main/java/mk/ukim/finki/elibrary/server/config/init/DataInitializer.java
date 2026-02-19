package mk.ukim.finki.elibrary.server.config.init;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.elibrary.server.model.domain.*;
import mk.ukim.finki.elibrary.server.model.enumerations.EmployeeType;
import mk.ukim.finki.elibrary.server.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

//ima metod koj generira mnogu podatoci () i malku podatoci
// kaj init e komentiran

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
        seedGenres();
        seedAuthors();
        seedUsers();
        seedBaseBooksAndCopies();
        seedRoomsAndSeats();
        seedEmployees();
        seedBorrowedBooksSample();
//        seedBookBorrowLogsBig();
        seedBookBorrowLogsSmall();
    }



    private void seedGenres() {
        if (genreRepository.count() > 0) return;

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("Fantasy"));
        genres.add(new Genre("Science Fiction"));
        genres.add(new Genre("Romance"));
        genres.add(new Genre("Mystery"));
        genres.add(new Genre("Thriller"));
        genres.add(new Genre("Historical Fiction"));
        genres.add(new Genre("Non-Fiction"));
        genres.add(new Genre("Biography"));
        genres.add(new Genre("Young Adult"));
        genres.add(new Genre("Dystopian"));
        genres.add(new Genre("Horror"));
        genres.add(new Genre("Classic"));
        genres.add(new Genre("Philosophy"));
        genres.add(new Genre("Self Help"));
        genres.add(new Genre("Poetry"));

        genreRepository.saveAll(genres);
        System.out.println("[DataInitializer] Seeded genres: " + genres.size());
    }


    private void seedAuthors() {
        if (authorRepository.count() > 0) return;

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("J.R.R. Tolkien"));
        authors.add(new Author("George Orwell"));
        authors.add(new Author("Jane Austen"));
        authors.add(new Author("Isaac Asimov"));
        authors.add(new Author("Stephen King"));
        authors.add(new Author("Yuval Noah Harari"));
        authors.add(new Author("Dan Brown"));
        authors.add(new Author("Agatha Christie"));
        authors.add(new Author("Brandon Sanderson"));
        authors.add(new Author("J.K. Rowling"));
        authors.add(new Author("C.S. Lewis"));
        authors.add(new Author("John Green"));

        authorRepository.saveAll(authors);
        System.out.println("[DataInitializer] Seeded authors: " + authors.size());
    }

    private void seedUsers() {


        ensureUser("Milena", "Milenovska", "milena.milenovska@example.com", true, 14);
        ensureUser("Marko", "Markovski", "marko.markovski@example.com", false, 7);
        ensureUser("Marija", "Gashoska", "marija.gashoska.1@students.finki.ukim.mk", true, 365);

        ensureUser("Elena", "Trajkovska", "elena.trajkovska@example.com", true, 300);
        ensureUser("Stefan", "Iliev", "stefan.iliev@example.com", true, 270);
        ensureUser("Ivan", "Georgiev", "ivan.georgiev@example.com", true, 240);
        ensureUser("Sara", "Nikolova", "sara.nikolova@example.com", true, 210);
        ensureUser("Nikola", "Arsov", "nikola.arsov@example.com", true, 180);
        ensureUser("Teodora", "Bojkovska", "teodora.bojkovska@example.com", true, 365);
        ensureUser("Viktor", "Kitanov", "viktor.kitanov@example.com", true, 365);
        ensureUser("Jana", "Aleksova", "jana.aleksova@example.com", true, 365);
        ensureUser("Filip", "Jovanovski", "filip.jovanovski@s.com", true, 365);
        ensureUser("Maja", "Dimitrova", "maja.dimitrova@example.com", true, 365);
        ensureUser("Bojan", "Ristov", "bojan.ristov@example.com", true, 365);
        ensureUser("Andrej", "Mitrevski", "andrej.mitrevski@example.com", true, 365);
        ensureUser("Kristina", "Popova", "kristina.popova@example.com", true, 365);

        System.out.println("[DataInitializer] Ensured users exist.");
    }


    private void seedBaseBooksAndCopies() {
        if (baseBookRepository.count() > 0 || bookCopyRepository.count() > 0) return;

        List<Genre> allGenres = genreRepository.findAll();
        List<Author> allAuthors = authorRepository.findAll();

        Genre fantasy = findGenre(allGenres, "Fantasy");
        Genre sciFi = findGenre(allGenres, "Science Fiction");
        Genre romance = findGenre(allGenres, "Romance");
        Genre dystopian = findGenre(allGenres, "Dystopian");
        Genre nonFiction = findGenre(allGenres, "Non-Fiction");
        Genre horror = findGenre(allGenres, "Horror");
        Genre thriller = findGenre(allGenres, "Thriller");
        Genre classic = findGenre(allGenres, "Classic");
        Genre mystery = findGenre(allGenres, "Mystery");
        Genre youngAdult = findGenre(allGenres, "Young Adult");


        Author tolkien = findAuthor(allAuthors, "J.R.R. Tolkien");
        Author orwell = findAuthor(allAuthors, "George Orwell");
        Author austen = findAuthor(allAuthors, "Jane Austen");
        Author asimov = findAuthor(allAuthors, "Isaac Asimov");
        Author king = findAuthor(allAuthors, "Stephen King");
        Author harari = findAuthor(allAuthors, "Yuval Noah Harari");
        Author brown = findAuthor(allAuthors, "Dan Brown");
        Author christie = findAuthor(allAuthors, "Agatha Christie");
        Author sanderson = findAuthor(allAuthors, "Brandon Sanderson");
        Author rowling = findAuthor(allAuthors, "J.K. Rowling");
        Author lewis = findAuthor(allAuthors, "C.S. Lewis");
        Author green = findAuthor(allAuthors, "John Green");

        List<BaseBook> books = new ArrayList<>();

        books.add(new BaseBook(
                "The Hobbit",
                tolkien,
                List.of(fantasy),
                LocalDate.of(1937, 9, 21),
                "Fantasy adventure about Bilbo Baggins.",
                10
        ));
        books.add(new BaseBook(
                "The Fellowship of the Ring",
                tolkien,
                List.of(fantasy),
                LocalDate.of(1954, 7, 29),
                "First part of The Lord of the Rings.",
                8
        ));
        books.add(new BaseBook(
                "1984",
                orwell,
                List.of(dystopian, classic),
                LocalDate.of(1949, 6, 8),
                "Dystopian novel about a totalitarian regime.",
                9
        ));
        books.add(new BaseBook(
                "Animal Farm",
                orwell,
                List.of(dystopian, classic),
                LocalDate.of(1945, 8, 17),
                "Political allegory with farm animals.",
                7
        ));
        books.add(new BaseBook(
                "Pride and Prejudice",
                austen,
                List.of(romance, classic),
                LocalDate.of(1813, 1, 28),
                "Classic romance novel.",
                6
        ));
        books.add(new BaseBook(
                "Foundation",
                asimov,
                List.of(sciFi),
                LocalDate.of(1951, 1, 1),
                "Sci-fi saga about the fall of a galactic empire.",
                7
        ));
        books.add(new BaseBook(
                "The Shining",
                king,
                List.of(horror),
                LocalDate.of(1977, 1, 28),
                "Horror novel set in an isolated hotel.",
                6
        ));
        books.add(new BaseBook(
                "Sapiens",
                harari,
                List.of(nonFiction),
                LocalDate.of(2011, 1, 1),
                "A brief history of humankind.",
                10
        ));
        books.add(new BaseBook(
                "Angels & Demons",
                brown,
                List.of(thriller),
                LocalDate.of(2000, 5, 1),
                "Thriller involving a secret society.",
                7
        ));
        books.add(new BaseBook(
                "Murder on the Orient Express",
                christie,
                List.of(mystery),
                LocalDate.of(1934, 1, 1),
                "Detective Hercule Poirot solves a murder on a train.",
                5
        ));
        books.add(new BaseBook(
                "Mistborn: The Final Empire",
                sanderson,
                List.of(fantasy),
                LocalDate.of(2006, 7, 17),
                "Epic fantasy with metal-based magic.",
                8
        ));
        books.add(new BaseBook(
                "Harry Potter and the Philosopher's Stone",
                rowling,
                List.of(fantasy, youngAdult),
                LocalDate.of(1997, 6, 26),
                "Boy discovers he is a wizard.",
                10
        ));
        books.add(new BaseBook(
                "The Lion, the Witch and the Wardrobe",
                lewis,
                List.of(fantasy, youngAdult),
                LocalDate.of(1950, 10, 16),
                "Children discover a magical land called Narnia.",
                7
        ));
        books.add(new BaseBook(
                "The Fault in Our Stars",
                green,
                List.of(romance, youngAdult),
                LocalDate.of(2012, 1, 10),
                "Romantic drama about two teenagers.",
                6
        ));

        baseBookRepository.saveAll(books);

        List<BookCopy> copies = new ArrayList<>();
        for (BaseBook b : books) {
            for (int i = 0; i < b.getNumBooks(); i++) {
                copies.add(new BookCopy(b));
            }
        }
        bookCopyRepository.saveAll(copies);

        System.out.println("[DataInitializer] Seeded base books: " + books.size());
        System.out.println("[DataInitializer] Seeded book copies: " + copies.size());
    }

    private void seedRoomsAndSeats() {
        if (roomRepository.count() > 0 || seatRepository.count() > 0) return;

        Room room1 = new Room("Sala 1", "Prv kat", 10, null);
        Room room2 = new Room("Sala 3", "Vtor kat", 8, null);
        roomRepository.saveAll(List.of(room1, room2));

        List<UserWrapper> users = userWrapperRepository.findAll();
        if (users.size() < 2) return;

        UserWrapper user1 = users.get(0);
        UserWrapper user2 = users.get(1);

        Seat seat1 = new Seat(1, false, user1, room1);
        Seat seat2 = new Seat(2, true, user2, room2);
        seatRepository.saveAll(List.of(seat1, seat2));

        System.out.println("[DataInitializer] Seeded rooms and seats.");
    }

    private void seedEmployees() {
        if (employeeRepository.count() > 0) return;

        List<UserWrapper> users = userWrapperRepository.findAll();
        if (users.size() < 2) return;

        UserWrapper user1 = users.get(0);
        UserWrapper user2 = users.get(1);

        Employee employee1 = new Employee( "admin", "admin123", "admin@mail.com", EmployeeType.ADMIN);
        employee1.setUser(user1);
        Employee employee2 = new Employee( "obichen", "ob123", "obichen@mail.com", EmployeeType.OBICHEN);

        employeeRepository.saveAll(List.of(employee1, employee2));
        System.out.println("[DataInitializer] Seeded employees.");
    }

    private void seedBorrowedBooksSample() {
        if (borrowedBookRepository.count() > 0) return;

        List<UserWrapper> users = userWrapperRepository.findAll();
        List<BookCopy> copies = bookCopyRepository.findAll();
        if (users.isEmpty() || copies.isEmpty()) return;

        UserWrapper user1 = users.get(0);
        BookCopy copy1 = copies.get(0);

        BorrowedBook borrowed1 = new BorrowedBook(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                user1,
                copy1
        );
        borrowedBookRepository.save(borrowed1);

        System.out.println("[DataInitializer] Seeded sample BorrowedBook.");
    }

    //Metod za mnogu podatoci
    private void seedBookBorrowLogsBig() {
        if (bookBorrowLogRepository.count() > 0) {
            System.out.println("[DataInitializer] BookBorrowLog already has data, skipping big seed.");
            return;
        }

        List<UserWrapper> users = userWrapperRepository.findAll();
        List<BookCopy> allCopies = bookCopyRepository.findAll();

        if (users.isEmpty() || allCopies.isEmpty()) {
            System.out.println("[DataInitializer] Cannot seed BookBorrowLog: no users or copies.");
            return;
        }

        Random random = new Random(42);
        List<BookBorrowLog> logs = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            UserWrapper user = users.get(random.nextInt(users.size()));
            List<String> preferredGenreNames = preferredGenreNamesForUser(user);
            List<BookCopy> preferredCopies = filterCopiesByGenres(allCopies, preferredGenreNames);
            BookCopy copy;
            if (!preferredCopies.isEmpty() && random.nextDouble() < 0.8) {
                copy = preferredCopies.get(random.nextInt(preferredCopies.size()));
            } else {
                copy = allCopies.get(random.nextInt(allCopies.size()));
            }

            LocalDate randomDate = LocalDate.of(2024, 1, 1)
                    .plusDays(random.nextInt(365));
            LocalTime randomTime = LocalTime.of(
                    8 + random.nextInt(12),
                    random.nextBoolean() ? 0 : 30
            );
            LocalDateTime borrowedAt = LocalDateTime.of(randomDate, randomTime);
            LocalDateTime dueDate = borrowedAt.plusDays(14);

            BookBorrowLog log = new BookBorrowLog();
            log.setUser(user);
            log.setBookCopy(copy);
            log.setBorrowedAt(borrowedAt);
            log.setDueDate(dueDate);

            if (random.nextBoolean()) {
                log.setReturnedAt(
                        borrowedAt.plusDays(7 + random.nextInt(14))
                );
            }

            log.setNotes("Segmented auto-generated log #" + (i + 1));
            logs.add(log);
        }
        ensureTestBookForUser16(users, allCopies, logs);
        bookBorrowLogRepository.saveAll(logs);
        System.out.println("[DataInitializer] Seeded segmented BookBorrowLog rows: " + logs.size());
    }

    //metod za testiranje
    private void ensureTestBookForUser16(List<UserWrapper> users,
                                         List<BookCopy> allCopies,
                                         List<BookBorrowLog> logs) {
        Long testUserId = 16L;

        UserWrapper testUser = users.stream()
                .filter(u -> testUserId.equals(u.getId()))
                .findFirst()
                .orElse(null);

        if (testUser == null) {
            System.out.println("[DataInitializer] User with id 16 not found, skipping test-case enforcement.");
            return;
        }


        BaseBook testBook = baseBookRepository.findAll().stream()
                .filter(b -> "The Lion, the Witch and the Wardrobe".equals(b.getTitle()))
                .findFirst()
                .orElse(null);

        if (testBook == null) {
            System.out.println("[DataInitializer] Test BaseBook not found, skipping test-case enforcement.");
            return;
        }


        List<BookCopy> copiesOfBook = allCopies.stream()
                .filter(c -> c.getBaseBook() != null
                        && testBook.getId().equals(c.getBaseBook().getId()))
                .toList();

        if (copiesOfBook.isEmpty()) {
            System.out.println("[DataInitializer] No copies for test book, skipping test-case enforcement.");
            return;
        }

        List<Long> copyIds = copiesOfBook.stream()
                .map(BookCopy::getId)
                .toList();


        logs.removeIf(l ->
                l.getUser() != null
                        && testUserId.equals(l.getUser().getId())
                        && l.getBookCopy() != null
                        && copyIds.contains(l.getBookCopy().getId())
        );


        Long freeCopyId = copiesOfBook.get(0).getId();

        for (BookBorrowLog l : logs) {
            if (l.getBookCopy() != null
                    && freeCopyId.equals(l.getBookCopy().getId())
                    && l.getReturnedAt() == null) {

                LocalDateTime borrowedAt = l.getBorrowedAt() != null
                        ? l.getBorrowedAt()
                        : LocalDateTime.now().minusDays(10);

                l.setReturnedAt(borrowedAt.plusDays(7));
            }
        }

        System.out.println("[DataInitializer] Ensured test book '" + testBook.getTitle()
                + "' has an available copy and has never been borrowed by user 16.");
    }


    private List<String> preferredGenreNamesForUser(UserWrapper user) {
        long id = user.getId() != null ? user.getId() : 0L;
        int segment = (int) ((id - 1 + 4) % 4);

        return switch (segment) {
            case 0 -> List.of("Fantasy", "Young Adult");
            case 1 -> List.of("Dystopian", "Classic");
            case 2 -> List.of("Romance", "Young Adult");
            case 3 -> List.of("Non-Fiction", "Science Fiction", "Thriller", "Mystery");
            default -> List.of("Fantasy");
        };
    }

    private List<BookCopy> filterCopiesByGenres(List<BookCopy> allCopies, List<String> genreNames) {
        if (genreNames == null || genreNames.isEmpty()) {
            return List.of();
        }

        List<String> lowerNames = genreNames.stream()
                .filter(Objects::nonNull)
                .map(s -> s.trim().toLowerCase())
                .toList();

        List<BookCopy> result = new ArrayList<>();
        for (BookCopy copy : allCopies) {
            BaseBook bb = copy.getBaseBook();
            if (bb == null || bb.getGenres() == null) continue;

            boolean matches = bb.getGenres().stream()
                    .map(Genre::getName)
                    .filter(Objects::nonNull)
                    .map(s -> s.trim().toLowerCase())
                    .anyMatch(lowerNames::contains);

            if (matches) {
                result.add(copy);
            }
        }
        return result;
    }


    private Genre findGenre(List<Genre> genres, String name) {
        return genres.stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing genre: " + name));
    }

    private Author findAuthor(List<Author> authors, String name) {
        return authors.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing author: " + name));
    }

    //metod za malku podatoci
    private void seedBookBorrowLogsSmall() {
        if (bookBorrowLogRepository.count() > 0) {
            System.out.println("[DataInitializer] BookBorrowLog already has data, skipping SMALL seed.");
            return;
        }

        List<UserWrapper> users = userWrapperRepository.findAll();
        List<BaseBook> books = baseBookRepository.findAll();
        List<BookCopy> copies = bookCopyRepository.findAll();

        if (users.isEmpty() || books.isEmpty() || copies.isEmpty()) {
            System.out.println("[DataInitializer] Cannot seed small BookBorrowLog: missing users/books/copies.");
            return;
        }

        UserWrapper milena = ensureUser("Milena", "Milenovska", "milena.milenovska@example.com", true, 14);
        UserWrapper marko  = ensureUser("Marko", "Markovski", "marko.markovski@example.com", false, 7);
        UserWrapper marija = ensureUser("Marija", "Gashoska", "marija.gashoska.1@students.finki.ukim.mk", true, 365);


        BaseBook hobbit       = findBookByTitle(books, "The Hobbit");
        BaseBook fellowship   = findBookByTitle(books, "The Fellowship of the Ring");
        BaseBook hpStone      = findBookByTitle(books, "Harry Potter and the Philosopher's Stone");
        BaseBook narnia       = findBookByTitle(books, "The Lion, the Witch and the Wardrobe");
        BaseBook foundation   = findBookByTitle(books, "Foundation");
        BaseBook sapiens      = findBookByTitle(books, "Sapiens");
        BaseBook pride        = findBookByTitle(books, "Pride and Prejudice");
        BaseBook murderOrient = findBookByTitle(books, "Murder on the Orient Express");

        BookCopy hobbitCopy       = findAnyCopyForBook(copies, hobbit);
        BookCopy fellowshipCopy   = findAnyCopyForBook(copies, fellowship);
        BookCopy hpStoneCopy      = findAnyCopyForBook(copies, hpStone);
        BookCopy narniaCopy       = findAnyCopyForBook(copies, narnia);
        BookCopy foundationCopy   = findAnyCopyForBook(copies, foundation);
        BookCopy sapiensCopy      = findAnyCopyForBook(copies, sapiens);
        BookCopy prideCopy        = findAnyCopyForBook(copies, pride);
        BookCopy murderOrientCopy = findAnyCopyForBook(copies, murderOrient);

        List<BookBorrowLog> logs = new ArrayList<>();

        logs.add(createLog(
                milena, hobbitCopy,
                LocalDateTime.of(2024, 1, 10, 10, 0),
                true
        ));
        logs.add(createLog(
                milena, fellowshipCopy,
                LocalDateTime.of(2024, 1, 20, 11, 30),
                true
        ));
        logs.add(createLog(
                milena, hpStoneCopy,
                LocalDateTime.of(2024, 2, 5, 14, 0),
                false
        ));
        logs.add(createLog(
                milena, prideCopy,
                LocalDateTime.of(2024, 2, 15, 16, 0),
                true
        ));

        logs.add(createLog(
                marko, sapiensCopy,
                LocalDateTime.of(2024, 1, 12, 9, 0),
                true
        ));
        logs.add(createLog(
                marko, sapiensCopy,
                LocalDateTime.of(2024, 3, 1, 9, 0),
                false
        ));
        logs.add(createLog(
                marko, foundationCopy,
                LocalDateTime.of(2024, 2, 1, 13, 0),
                true
        ));

        logs.add(createLog(
                marija, hobbitCopy,
                LocalDateTime.of(2024, 1, 18, 15, 0),
                true
        ));
        logs.add(createLog(
                marija, murderOrientCopy,
                LocalDateTime.of(2024, 2, 10, 12, 0),
                false
        ));
        logs.add(createLog(
                marija, prideCopy,
                LocalDateTime.of(2024, 3, 5, 17, 0),
                true
        ));

        bookBorrowLogRepository.saveAll(logs);
        System.out.println("[DataInitializer] Seeded SMALL BookBorrowLog rows: " + logs.size());
    }
    private UserWrapper findUserByEmail(List<UserWrapper> users, String email) {
        return users.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Missing user with email: " + email));
    }

    private BaseBook findBookByTitle(List<BaseBook> books, String title) {
        return books.stream()
                .filter(b -> title.equalsIgnoreCase(b.getTitle()))
                .findFirst()
               .orElseThrow(() -> new IllegalStateException("Missing book: " + title));

    }

    private BookCopy findAnyCopyForBook(List<BookCopy> copies, BaseBook book) {
        return copies.stream()
                .filter(c -> c.getBaseBook() != null
                        && book.getId().equals(c.getBaseBook().getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No copies for book: " + book.getTitle()));
    }

    private BookBorrowLog createLog(UserWrapper user,
                                    BookCopy copy,
                                    LocalDateTime borrowedAt,
                                    boolean returned) {

        BookBorrowLog log = new BookBorrowLog();
        log.setUser(user);
        log.setBookCopy(copy);
        log.setBorrowedAt(borrowedAt);
        log.setDueDate(borrowedAt.plusDays(14));

        if (returned) {
            log.setReturnedAt(borrowedAt.plusDays(7));
        }

        log.setNotes("SMALL seed log for ML testing");
        return log;
    }


    private UserWrapper ensureUser(String firstName,
                                   String lastName,
                                   String email,
                                   boolean active,
                                   int membershipDays) {

        // 1) try find existing by email (in DB)
        UserWrapper existing = userWrapperRepository.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().trim().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            // normalize email just in case it has spaces
            if (!existing.getEmail().equals(existing.getEmail().trim())) {
                existing.setEmail(existing.getEmail().trim());
                userWrapperRepository.save(existing);
            }
            return existing;
        }

        // 2) create if missing
        UserWrapper u = new UserWrapper(
                firstName,
                lastName,
                LocalDate.now(),
                LocalDate.now().plusDays(membershipDays),
                active,
                email.trim()
        );

        return userWrapperRepository.save(u);
    }


}
