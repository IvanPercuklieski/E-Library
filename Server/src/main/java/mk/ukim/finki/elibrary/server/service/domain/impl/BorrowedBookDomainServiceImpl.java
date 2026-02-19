package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedBookDomainServiceImpl implements BorrowedBookDomainService {
    @Override
    public DisplayBorrowedBookDto createBookBorrowing(CreateBorrowedBookDto bookCopy) {
        return null;
    }

    @Override
    public DisplayBorrowedBookDto updateBookBorrowing(Long id, UpdateBorrowedBookDto bookCopy) {
        return null;
    }

    @Override
    public void deleteBookBorrowing(Long bookCopyId) {

    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowings() {
        return List.of();
    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowingsByBook(Long bookId) {
        return List.of();
    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowingsByUser(Long bookCopyId) {
        return List.of();
    }

    @Override
    public DisplayBorrowedBookDto getById(Long borrowingId) {
        return null;
    }
}
