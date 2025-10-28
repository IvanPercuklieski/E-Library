package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

public record DisplayBookCopyDto(Long id, Long baseBookId, String baseBookTitle) {

    public static DisplayBookCopyDto from(BookCopy copy) {
        return new DisplayBookCopyDto(
                copy.getId(),
                copy.getBaseBook().getId(),
                copy.getBaseBook().getTitle()
        );
    }

}
