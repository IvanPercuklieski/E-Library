package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.Seat;
import java.util.List;
import java.util.stream.Collectors;

public record DisplaySeatDto(Long id,
                             int seatNumber,
                             boolean isTaken,
                             Long userId,
                             Long roomId) {

    public static DisplaySeatDto from(Seat seat) {
        return new DisplaySeatDto(
                seat.getId(),
                seat.getSeatNumber(),
                seat.isTaken(),
                seat.getUser() != null ? seat.getUser().getId() : null,
                seat.getRoom() != null ? seat.getRoom().getId() : null
        );
    }

    public static List<DisplaySeatDto> from(List<Seat> seats) {
        return seats.stream()
                .map(DisplaySeatDto::from)
                .collect(Collectors.toList());
    }
}
