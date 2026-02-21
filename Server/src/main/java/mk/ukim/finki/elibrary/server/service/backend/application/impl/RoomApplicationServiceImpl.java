package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateRoomDto;
import mk.ukim.finki.elibrary.server.dto.DisplayRoomDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateRoomDto;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.RoomApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.RoomDomainService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoomApplicationServiceImpl implements RoomApplicationService {

    private final RoomDomainService roomDomainService;
    private final SeatDomainService seatDomainService;


    public RoomApplicationServiceImpl(RoomDomainService roomDomainService, SeatDomainService seatDomainService) {
        this.roomDomainService = roomDomainService;
        this.seatDomainService = seatDomainService;

    }


    @Override
    public Room updateRoom(Room room) {
        return roomDomainService.updateRoom(room);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomDomainService.deleteRoom(roomId);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomDomainService.getRoomById(roomId);
    }


    @Override
    public List<Seat> getSeatsInRoom(Long roomId) {
        return roomDomainService.getSeatsInRoom(roomId);
    }


    @Override
    public DisplayRoomDto createRoom(CreateRoomDto dto) {
        Room room = new Room(dto.name(), dto.location(), dto.numSeats(), new ArrayList<>());
        Room saved = roomDomainService.createRoom(room);


        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= dto.numSeats(); i++) {
            seats.add(new Seat(i, false, null, saved));
        }
        seatDomainService.saveAllSeats(seats);
        saved.setSeats(seats);

        return DisplayRoomDto.from(saved);
    }

    @Override
    public DisplayRoomDto updateRoom(Long id, UpdateRoomDto dto) {
        // fetch room
        Room room = roomDomainService.getRoomById(id);

        // update name/location
        if (dto.name() != null) room.setName(dto.name());
        if (dto.location() != null) room.setLocation(dto.location());

        // handle seats if numSeats changed
        if (dto.numSeats() != null && dto.numSeats() != room.getNumSeats()) {
            int oldSeats = room.getNumSeats();
            int newSeats = dto.numSeats();

            if (newSeats > oldSeats) {
                // add new seats
                List<Seat> newSeatList = new ArrayList<>();
                for (int i = oldSeats + 1; i <= newSeats; i++) {
                    newSeatList.add(new Seat(i, false, null, room));
                }
                seatDomainService.saveAllSeats(newSeatList);
                room.getSeats().addAll(newSeatList);
            } else {
                // remove seats safely
                List<Seat> seatsToRemove = room.getSeats().stream()
                        .filter(s -> s.getSeatNumber() > newSeats)
                        .toList();

                boolean hasReserved = seatsToRemove.stream().anyMatch(Seat::isTaken);
                if (hasReserved) {
                    throw new ResponseStatusException(
                            HttpStatus.PRECONDITION_FAILED,
                            "Cannot reduce seats - some seats are reserved"
                    );
                }

                // correct domain service call
                seatDomainService.deleteSeats(seatsToRemove);
                room.getSeats().removeAll(seatsToRemove);
            }

            room.setNumSeats(newSeats);
        }

        Room updated = roomDomainService.saveRoomEntity(room);
        return DisplayRoomDto.from(updated);
    }

    @Override
    public DisplayRoomDto getRoom(Long id) {
        Room room = roomDomainService.getRoomById(id);
        return DisplayRoomDto.from(room);
    }

    @Override
    public List<DisplayRoomDto> getAllRooms() {
        List<Room> rooms = roomDomainService.getAllRooms();
        return rooms.stream()
                .map(DisplayRoomDto::from)
                .toList();
    }

    @Override
    public List<DisplayRoomDto> getRoomsWithAvailableSeats() {

        List<Room> rooms = roomDomainService.getAllRooms();


        List<Room> roomsWithFreeSeats = rooms.stream()
                .filter(r -> seatDomainService.countAvailableSeats(r.getId()) > 0)
                .toList();


        return roomsWithFreeSeats.stream()
                .map(DisplayRoomDto::from)
                .toList();
    }

}

