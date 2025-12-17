package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.RoomApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.RoomDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomApplicationServiceImpl implements RoomApplicationService {

    private final RoomDomainService roomDomainService;

    public RoomApplicationServiceImpl(RoomDomainService roomDomainService) {
        this.roomDomainService = roomDomainService;
    }

    @Override
    public Room createRoom(Room room) {
        return roomDomainService.createRoom(room);
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
    public List<Room> getAllRooms() {
        return roomDomainService.getAllRooms();
    }

    @Override
    public List<Seat> getSeatsInRoom(Long roomId) {
        return roomDomainService.getSeatsInRoom(roomId);
    }

    @Override
    public boolean isSeatAvailable(Long seatId) {
        return roomDomainService.isSeatAvailable(seatId);
    }

    @Override
    public void reserveSeat(Long seatId, Long userId) {
        roomDomainService.reserveSeat(seatId, userId);
    }

    @Override
    public void releaseSeat(Long seatId) {
        roomDomainService.releaseSeat(seatId);
    }
}
