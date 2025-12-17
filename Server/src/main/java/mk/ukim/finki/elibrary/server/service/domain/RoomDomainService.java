package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;

import java.util.List;


public interface RoomDomainService {

    Room createRoom(Room room);

    Room updateRoom(Room room);

    void deleteRoom(Long roomId);

    Room getRoomById(Long roomId);

    List<Room> getAllRooms();

    List<Seat> getSeatsInRoom(Long roomId);

    boolean isSeatAvailable(Long seatId);

    void reserveSeat(Long seatId, Long userId);

    void releaseSeat(Long seatId);
}
