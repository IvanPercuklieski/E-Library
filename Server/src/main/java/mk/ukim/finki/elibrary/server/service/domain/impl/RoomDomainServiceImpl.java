package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.exceptions.RoomNotFoundException;
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
    public List<SeatDto> getSeatsInRoom(Long roomId) {
        Room room = getRoomById(roomId);

        return room.getSeats().stream().map(seat -> {
            UserDto userDto = null;

            if (seat.getUser() != null) {
                UserWrapper user = seat.getUser();
                userDto = new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname()
                );
            }

            return new SeatDto(
                    seat.getId(),
                    seat.getSeatNumber(),
                    userDto
            );
        }).toList();
    }

    @Override
    public Room saveRoomEntity(Room room) {
        return roomRepository.save(room);
    }

    // =========================
    // 🔥 DTOs INSIDE SAME FILE
    // =========================

    public static class SeatDto {
        private Long id;
        private Integer seatNumber;
        private UserDto user;

        public SeatDto(Long id, Integer seatNumber, UserDto user) {
            this.id = id;
            this.seatNumber = seatNumber;
            this.user = user;
        }

        public Long getId() { return id; }
        public Integer getSeatNumber() { return seatNumber; }
        public UserDto getUser() { return user; }

        public void setId(Long id) { this.id = id; }
        public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }
        public void setUser(UserDto user) { this.user = user; }
    }

    public static class UserDto {
        private Long id;
        private String name;
        private String surname;

        public UserDto(Long id, String name, String surname) {
            this.id = id;
            this.name = name;
            this.surname = surname;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getSurname() { return surname; }

        public void setId(Long id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setSurname(String surname) { this.surname = surname; }
    }
}