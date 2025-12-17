package mk.ukim.finki.elibrary.server.service.domain.impl;


import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.exceptions.RoomNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.SeatAlreadyTakenException;
import mk.ukim.finki.elibrary.server.model.exceptions.SeatNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.UserWrapperNotFoundException;
import mk.ukim.finki.elibrary.server.repository.RoomRepository;
import mk.ukim.finki.elibrary.server.repository.SeatRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.RoomDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomDomainServiceImpl implements RoomDomainService {

    private final RoomRepository roomRepository;
    private final SeatRepository seatRepository;
    private final UserWrapperRepository userRepository;

    public RoomDomainServiceImpl(RoomRepository roomRepository,
                                 SeatRepository seatRepository,
                                 UserWrapperRepository userRepository) {
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room room) {
        if (!roomRepository.existsById(room.getId())) {
            throw new RoomNotFoundException(room.getId());
        }
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Seat> getSeatsInRoom(Long roomId) {
        Room room = getRoomById(roomId);
        return room.getSeats();
    }

    @Override
    public boolean isSeatAvailable(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException(seatId));
        return !seat.isTaken();
    }

    @Override
    public void reserveSeat(Long seatId, Long userId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException(seatId));

        if (seat.isTaken()) {
            throw new SeatAlreadyTakenException(seatId);
        }

        UserWrapper user = userRepository.findById(userId)
                .orElseThrow(() -> new UserWrapperNotFoundException(userId));

        seat.setTaken(true);
        seat.setUser(user);
        seatRepository.save(seat);
    }

    @Override
    public void releaseSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException(seatId));

        seat.setTaken(false);
        seat.setUser(null);
        seatRepository.save(seat);
    }
}
