package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.service.domain.impl.RoomDomainServiceImpl;

import java.util.List;


public interface RoomDomainService {

    Room createRoom(Room room);

    Room updateRoom(Room room);

    void deleteRoom(Long roomId);

    Room getRoomById(Long roomId);

    List<Room> getAllRooms();

    List<RoomDomainServiceImpl.SeatDto> getSeatsInRoom(Long roomId);


    Room saveRoomEntity(Room room);
}
