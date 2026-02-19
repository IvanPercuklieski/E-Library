package mk.ukim.finki.elibrary.server.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBaseBookDto;
import mk.ukim.finki.elibrary.server.model.domain.*;
import mk.ukim.finki.elibrary.server.model.exceptions.*;
import mk.ukim.finki.elibrary.server.repository.*;
import mk.ukim.finki.elibrary.server.service.domain.BookDomainService;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class BookDomainServiceImpl implements BookDomainService {

    private final BaseBookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final BorrowedBookLogRepository borrowLogRepository;
    private final UserWrapperRepository userRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;


    public BookDomainServiceImpl(BaseBookRepository bookRepository,
                                 BookCopyRepository bookCopyRepository,
                                 BorrowedBookRepository borrowedBookRepository,
                                 BorrowedBookLogRepository borrowLogRepository,
                                 UserWrapperRepository userRepository,
                                 AuthorRepository authorRepository,
                                 GenreRepository genreRepository,
                                 ReviewRepository reviewRepository
                                ){
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.borrowLogRepository = borrowLogRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.reviewRepository = reviewRepository;

    }

    @Override
    public BaseBook createBook(BaseBook book) {
        return bookRepository.save(book);
    }

    @Override
    public BaseBook updateBook(Long id, UpdateBaseBookDto dto) {

        BaseBook book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));


        if (dto.title() != null) book.setTitle(dto.title());
        if (dto.pubDate() != null) book.setPubDate(dto.pubDate());
        if (dto.description() != null) book.setDescription(dto.description());
        book.setNumBooks(dto.requestedTotalCopies());


        if (dto.authorId() != null) {
            Author author = authorRepository.findById(dto.authorId())
                    .orElseThrow(() -> new AuthorNotFoundException(dto.authorId()));
            book.setAuthor(author);
        }


        if (dto.genreIds() != null) {
            List<Genre> genres = dto.genreIds().isEmpty()
                    ? List.of()
                    : genreRepository.findAllById(dto.genreIds());
            book.setGenres(genres);
        }

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public BaseBook getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Override
    public List<BaseBook> getAllBooks() {
        return bookRepository.findAll();
    }


    @Override
    public BorrowedBook borrowBook(Long userId, Long bookCopyId, LocalDateTime borrowDate, LocalDateTime dueDate) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));

        boolean isBorrowed = borrowedBookRepository.existsByBookCopy(copy);
        if (isBorrowed) {
            throw new BookAlreadyBorrowedException(bookCopyId);
        }

        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));

        BorrowedBook borrowed = new BorrowedBook();
        borrowed.setUser(user);
        borrowed.setBookCopy(copy);
        borrowed.setBorrowedAt(borrowDate);
        borrowed.setDueDate(dueDate);
        borrowedBookRepository.save(borrowed);

        BookBorrowLog log = new BookBorrowLog();
        log.setBookCopy(copy);
        log.setUser(user);
        log.setBorrowedAt(borrowDate);
        log.setDueDate(dueDate);
        borrowLogRepository.save(log);

        return borrowed;
    }

    @Override
    public void returnBook(Long borrowedBookId, LocalDate returnDate) {
        BorrowedBook borrowed = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException(borrowedBookId));

        borrowedBookRepository.delete(borrowed);

        BookBorrowLog log = borrowLogRepository.findByBookCopyAndUserAndReturnedAtIsNull(
                        borrowed.getBookCopy(), borrowed.getUser())
                .orElseThrow(() -> new RuntimeException("Borrow log not found"));

        log.setReturnedAt(returnDate.atStartOfDay());
        borrowLogRepository.save(log);
    }

    @Override
    public List<BookCopy> getAvailableBookCopies(Long bookId) {
        return bookCopyRepository.findAvailableBookCopies(bookId);
    }

    @Override
    public List<BaseBook> getRecommendedBooksForUser(Long userId) {
        return bookRepository.findRecommendedBooks(userId);
    }

    @Override
    public List<BaseBook> searchBooks(String title, Long authorId, List<Long> genreIds) {
//        Author author = (authorId != null) ? authorRepository.findById(authorId).orElse(null) : null;
//        List<Genre> genres = (genreIds != null && !genreIds.isEmpty()) ? genreRepository.findAllById(genreIds) : null;
        return bookRepository.searchBooks(title, authorId, genreIds);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUser(Long userId) {
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        return borrowedBookRepository.findByUser(user);
    }

    @Override
    public List<BookBorrowLog> getBorrowHistory(Long bookCopyId) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
        return borrowLogRepository.findByBookCopy(copy);
    }

    @Override
    public double calculateRentalFee(Long userId, int days) {
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        List<BorrowedBook> borrowed = borrowedBookRepository.findByUser(user);
        return borrowed.size() * days * 10.0;
    }

    @Override
    public boolean isBookAvailable(Long bookCopyId) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
        return !borrowedBookRepository.existsByBookCopy(copy);
    }

    @Override
    public List<BaseBook> getBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<BaseBook> getBooksByGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
        return bookRepository.findByGenresContains(genre);
    }

    @Override
    public void addReview(Long bookId, Long userId, String text, int rating) {
        BaseBook book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));
        Review review = new Review(book, user, text, rating);
        reviewRepository.save(review);
    }

    @Override
    public long countTotalCopies(Long bookId) {
        return bookCopyRepository.countByBaseBookId(bookId);
    }

    @Override
    public long countActiveBorrowings(Long bookId) {
        return borrowedBookRepository.countActiveBorrowingsByBaseBookId(bookId);
    }

}
