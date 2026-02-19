package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DisplayUserWrapperDto(Long id,
                                    String name,
                                    String surname,
                                    LocalDate fromDate,
                                    LocalDate dueDate,
                                    boolean isMember,
                                    List<String> borrowedBookTitles) {

    public static DisplayUserWrapperDto from(UserWrapper user) {
        List<String> borrowedTitles = user.getBorrowedBooks() != null
                ? user.getBorrowedBooks().stream()
                .map(b -> b.getBookCopy().getBaseBook().getTitle())
                .collect(Collectors.toList())
                : List.of();

        return new DisplayUserWrapperDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getFromDate(),
                user.getDueDate(),
                user.isMember(),
                borrowedTitles
        );
    }
    public static List<DisplayUserWrapperDto> from(List<UserWrapper> users) {
        return users.stream()
                .map(DisplayUserWrapperDto::from)
                .collect(Collectors.toList());
    }
}
