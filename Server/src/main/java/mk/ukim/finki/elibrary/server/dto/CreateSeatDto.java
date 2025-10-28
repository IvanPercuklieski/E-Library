package mk.ukim.finki.elibrary.server.dto;

import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;

public record CreateSeatDto(int seatNumber,
                            boolean isTaken,
                            Long userId,
                            Long roomId) {


    public Seat toSeat(UserWrapper user, Room room) {
        return new Seat(seatNumber, isTaken, user, room);
    }


}
