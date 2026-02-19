package mk.ukim.finki.elibrary.server.dto.display;

import mk.ukim.finki.elibrary.server.model.domain.BookCopy;

import java.util.List;


public record DisplayBookCopyDto(Long id, Long baseBookId, String baseBookTitle, Boolean isAvailable) {

    public static DisplayBookCopyDto from(BookCopy copy) {
        return new DisplayBookCopyDto(
                copy.getId(),
                copy.getBaseBook().getId(),
                copy.getBaseBook().getTitle(),
                copy.getIsAvailable()
        );
    }
    public static List<DisplayBookCopyDto> from(List<BookCopy> bookCopies){
        return bookCopies.stream().map(DisplayBookCopyDto::from).toList() ;
    }

}
