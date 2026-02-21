package mk.ukim.finki.elibrary.server.service.backend.application;
import mk.ukim.finki.elibrary.server.dto.CreateRoomDto;
import mk.ukim.finki.elibrary.server.dto.DisplayRoomDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateRoomDto;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;

import java.util.List;

public interface RoomApplicationService {

    DisplayRoomDto createRoom(CreateRoomDto dto);

    Room updateRoom(Room room);

    void deleteRoom(Long roomId);

    Room getRoomById(Long roomId);

    //List<Room> getAllRooms();

    List<Seat> getSeatsInRoom(Long roomId);

    List<DisplayRoomDto> getRoomsWithAvailableSeats();

    DisplayRoomDto updateRoom(Long id, UpdateRoomDto dto);

    DisplayRoomDto getRoom(Long id);

    List<DisplayRoomDto> getAllRooms();
}
