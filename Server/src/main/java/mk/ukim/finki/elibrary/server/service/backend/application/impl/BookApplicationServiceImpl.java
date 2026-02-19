package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import jakarta.transaction.Transactional;

import mk.ukim.finki.elibrary.server.dto.BookDetailsDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBaseDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBaseBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBaseBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBaseBookDto;
import mk.ukim.finki.elibrary.server.model.domain.*;
import mk.ukim.finki.elibrary.server.model.exceptions.AuthorNotFoundException;
import mk.ukim.finki.elibrary.server.repository.AuthorRepository;
import mk.ukim.finki.elibrary.server.repository.GenreRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.BookApplicationService;
import mk.ukim.finki.elibrary.server.service.backend.application.ReviewApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BookDomainService;
import mk.ukim.finki.elibrary.server.service.domain.ReviewDomainService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class BookApplicationServiceImpl implements BookApplicationService {

    private final BookDomainService bookDomainService;
    private final ReviewApplicationService reviewApplicationService;
    private final ReviewDomainService reviewDomainService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookApplicationServiceImpl(BookDomainService bookDomainService, ReviewApplicationService reviewApplicationService, ReviewDomainService reviewDomainService, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookDomainService = bookDomainService;
        this.reviewApplicationService = reviewApplicationService;
        this.reviewDomainService = reviewDomainService;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public DisplayBookBaseDto createBook(CreateBaseBookDto dto) {

        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new AuthorNotFoundException(dto.authorId()));

        List<Genre> genres = (dto.genreIds() == null || dto.genreIds().isEmpty())
                ? List.of()
                : genreRepository.findAllById(dto.genreIds());

        BaseBook book = new BaseBook(
                dto.title(),
                author,
                genres,
                dto.pubDate(),
                dto.description(),
                dto.numBooks()
        );

        return DisplayBookBaseDto.from( bookDomainService.createBook(book));
    }
   //bookDomainService.updateBook(book);
    @Override
    public DisplayBaseBookDto updateBook(Long bookId, UpdateBaseBookDto book) {
        BaseBook saved=bookDomainService.updateBook(bookId,book);
        long total = bookDomainService.countTotalCopies(saved.getId());
        long active = bookDomainService.countActiveBorrowings(saved.getId());
        long available = total - active;
        return DisplayBaseBookDto.from(saved,total,available,active);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookDomainService.deleteBook(bookId);
    }

    @Override
    public DisplayBaseBookDto getBookById(Long bookId) {

        long total = bookDomainService.countTotalCopies(bookId);
        long active = bookDomainService.countActiveBorrowings(bookId);
        return DisplayBaseBookDto.from(bookDomainService.getBookById(bookId),total,total-active,active);

    }

    @Override
    public List<DisplayBaseBookDto> getAllBooks() {
        List<BaseBook> books = bookDomainService.getAllBooks();

        return DisplayBaseBookDto.from(books, bookId -> {
            long total = bookDomainService.countTotalCopies(bookId);
            long active = bookDomainService.countActiveBorrowings(bookId);
            long available = total - active;
            return new DisplayBaseBookDto.Counters(total, available, active);
        });
    }


    @Override
    public List<DisplayBaseBookDto> searchBooks(String title, Long authorId, List<Long> genreIds) {
        List<BaseBook> books = bookDomainService.searchBooks(title, authorId, genreIds);

        return books.stream()
                .map(b -> {
                    long total = bookDomainService.countTotalCopies(b.getId());
                    long active = bookDomainService.countActiveBorrowings(b.getId());
                    long available = total - active;
                    return DisplayBaseBookDto.from(b, total, available, active);
                })
                .toList();
    }

    @Override
    public List<BaseBook> getRecommendedBooksForUser(Long userId) {
        return bookDomainService.getRecommendedBooksForUser(userId);
    }

    @Override
    public List<BookCopy> getAvailableBookCopies(Long bookId) {
        return bookDomainService.getAvailableBookCopies(bookId);
    }

    @Override
    public BookDetailsDto getBookDetails(Long bookId) {
        BaseBook book = bookDomainService.getBookById(bookId);
        int availableCopies = bookDomainService.getAvailableBookCopies(bookId).size();

        List<ReviewDisplayDto> reviews = reviewApplicationService.getReviewsForBook(bookId);

        return new BookDetailsDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenres().stream().map(Genre::getName).toList(),
                book.getPubDate(),
                book.getDescription(),
                availableCopies,
                reviews
        );
    }

    @Override
    public void addReview(Long bookId, Long userId, String text, int rating) {
        reviewApplicationService.addReview(bookId, userId, text, rating);

    }

    @Override
    public List<ReviewDisplayDto> getReviewsForBook(Long bookId) {
        List<Review> reviews = reviewDomainService.getReviewsForBook(bookId);
        return ReviewDisplayDto.from(reviews);
    }
}
