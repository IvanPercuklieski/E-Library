package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookCopyDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBookCopyDto;
import mk.ukim.finki.elibrary.server.service.backend.application.BookCopyApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BookCopyDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyApplicationServiceImpl implements BookCopyApplicationService {

    private final BookCopyDomainService bookCopyDomainService;

    public BookCopyApplicationServiceImpl(BookCopyDomainService bookCopyDomainService) {
        this.bookCopyDomainService = bookCopyDomainService;
    }

    @Override
    public DisplayBookCopyDto createBookCopy(CreateBookCopyDto bookCopy) {
        return  DisplayBookCopyDto.from(bookCopyDomainService.createBookCopy(bookCopy));

    }

    @Override
    public void deleteBookCopy(Long bookCopyId) {
        bookCopyDomainService.deleteBookCopy(bookCopyId);
    }

    @Override
    public void deleteAllByBook(Long bookId) {
    bookCopyDomainService.deleteAllByBook(bookId);
    }

    @Override
    public List<DisplayBookCopyDto> getAllBookCopies() {
        return DisplayBookCopyDto.from(bookCopyDomainService.getAllBookCopies());
    }

    @Override
    public List<DisplayBookCopyDto> getAllBookCopiesByBook(Long bookId) {
        return DisplayBookCopyDto.from(bookCopyDomainService.getAllBookCopiesByBook(bookId));
    }

    @Override
    public DisplayBookCopyDto getBookCopyById(Long bookCopyId) {
        return DisplayBookCopyDto.from(bookCopyDomainService.getBookCopyById(bookCopyId));
    }
}
