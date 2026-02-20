package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.display.DisplayBookBorrowLogDto;

import java.util.List;

public interface BorrowedBookLogApplicationService {
    List<DisplayBookBorrowLogDto> getAll();
    List<DisplayBookBorrowLogDto>getAllForUser(Long userId);
    List<DisplayBookBorrowLogDto>getAllForBookCopy(Long bookCopyId);
    void deleteAllForBookCopy(Long bookCopyId);
    void deleteAllForUser(Long userId);
    void deleteAll();
}
