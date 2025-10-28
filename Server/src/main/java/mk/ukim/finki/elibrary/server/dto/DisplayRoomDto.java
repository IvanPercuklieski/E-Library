package mk.ukim.finki.elibrary.server.dto;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayRoomDto(Long id,
                             String name,
                             String location,
                             int numSeats,
                             List<Long> seatIds) {

    public static DisplayRoomDto from(Room room) {
        List<Long> seatIds = room.getSeats() != null
                ? room.getSeats().stream().map(Seat::getId).collect(Collectors.toList())
                : List.of();

        return new DisplayRoomDto(
                room.getId(),
                room.getName(),
                room.getLocation(),
                room.getNumSeats(),
                seatIds
        );
    }

    public static List<DisplayRoomDto> from(List<Room> rooms) {
        return rooms.stream()
                .map(DisplayRoomDto::from)
                .collect(Collectors.toList());
    }
}
