package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.create.CreateBookBorrowLogDto;
import mk.ukim.finki.elibrary.server.dto.create.CreateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.display.DisplayBorrowedBookDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateBorrowedBookDto;
import mk.ukim.finki.elibrary.server.service.backend.application.BorrowedBookApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.BorrowedBookDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowedBookApplicationServiceImpl implements BorrowedBookApplicationService {

    private final BorrowedBookDomainService borrowedBookDomainService;

    public BorrowedBookApplicationServiceImpl(BorrowedBookDomainService borrowedBookDomainService) {
        this.borrowedBookDomainService = borrowedBookDomainService;
    }

    @Override
    public DisplayBorrowedBookDto createBookBorrowing(CreateBorrowedBookDto dto) {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.createBookBorrowing(dto));
    }

    @Override
    public DisplayBorrowedBookDto updateBookBorrowing(Long id, UpdateBorrowedBookDto dto) {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.updateBookBorrowing(id,dto));
    }

    @Override
    public void deleteBookBorrowing(CreateBookBorrowLogDto dto) {
        borrowedBookDomainService.deleteBookBorrowing(dto);
    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowings() {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.getAllBookBorrowings());
    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowingsByBook(Long bookId) {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.getAllBookBorrowingsByBook(bookId));
    }

    @Override
    public List<DisplayBorrowedBookDto> getAllBookBorrowingsByUser(Long userId) {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.getAllBookBorrowingsByUser(userId));
    }

    @Override
    public DisplayBorrowedBookDto getById(Long borrowingId) {
        return DisplayBorrowedBookDto.from(borrowedBookDomainService.getById(borrowingId));
    }
}
