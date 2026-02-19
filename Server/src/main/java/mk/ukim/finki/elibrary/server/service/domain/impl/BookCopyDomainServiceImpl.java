package mk.ukim.finki.elibrary.server.service.domain.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.model.domain.BaseBook;
import mk.ukim.finki.elibrary.server.model.domain.BookCopy;
import mk.ukim.finki.elibrary.server.model.exceptions.BookCopyNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.BookNotFoundException;
import mk.ukim.finki.elibrary.server.repository.BaseBookRepository;
import mk.ukim.finki.elibrary.server.repository.BookCopyRepository;
import mk.ukim.finki.elibrary.server.service.domain.BookCopyDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyDomainServiceImpl implements BookCopyDomainService {

    private final BookCopyRepository bookCopyRepository;
    private final BaseBookRepository baseBookRepository;

    public BookCopyDomainServiceImpl(BookCopyRepository bookCopyRepository, BaseBookRepository baseBookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.baseBookRepository = baseBookRepository;
    }

    @Override
    public BookCopy createBookCopy(CreateBookCopyDto bookCopy) {
        BaseBook temp=baseBookRepository.findById(bookCopy.bookId()).orElseThrow(()->new BookNotFoundException(bookCopy.bookId()));
        BookCopy copy=new BookCopy(temp);
        return bookCopyRepository.save(copy);
    }

    @Override
    public void deleteBookCopy(Long bookId) {
        bookCopyRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void deleteAllByBook(Long bookId) {
        List<BookCopy> copies = bookCopyRepository.findByBaseBookId(bookId);
        bookCopyRepository.deleteAll(copies);
    }

    @Override
    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    @Override
    public List<BookCopy> getAllBookCopiesByBook(Long bookId) {
        return bookCopyRepository.findByBaseBookId(bookId);
    }

    @Override
    public BookCopy getBookCopyById(Long bookCopyId) {
        return bookCopyRepository.findById(bookCopyId).orElseThrow(()->new BookCopyNotFoundException(bookCopyId));
    }
}
