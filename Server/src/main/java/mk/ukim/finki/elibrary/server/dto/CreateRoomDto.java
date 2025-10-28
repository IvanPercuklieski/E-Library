package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.Room;

public record CreateRoomDto(String name,
                            String location,
                            int numSeats) {

    public Room toRoom() {
        return new Room(name, location, numSeats, null);
    }

}
