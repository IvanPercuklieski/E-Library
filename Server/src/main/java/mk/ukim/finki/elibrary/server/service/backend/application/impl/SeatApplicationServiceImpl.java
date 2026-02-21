package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateSeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.RoomRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.RoomApplicationService;
import mk.ukim.finki.elibrary.server.service.backend.application.SeatApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.RoomDomainService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.domain.UserWrapperService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;

import java.util.List;

@Service
public class SeatApplicationServiceImpl implements SeatApplicationService {

    private final SeatDomainService seatDomainService;
    private final UserWrapperRepository userRepository;
    private final RoomRepository roomRepository;
    private final UserWrapperService userDomainService;
    private final RoomDomainService roomDomainService;


    public SeatApplicationServiceImpl(
            SeatDomainService seatDomainService,
            UserWrapperRepository userRepository,
            RoomRepository roomRepository, UserWrapperService userDomainService, RoomDomainService roomDomainService
    ) {
        this.seatDomainService = seatDomainService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.userDomainService = userDomainService;

        this.roomDomainService = roomDomainService;
    }

    @Override
    public List<DisplaySeatDto> getAllSeats() {
        List<Seat> seats = seatDomainService.getAllSeats();
        return DisplaySeatDto.from(seats);
    }

    @Override
    public List<DisplaySeatDto> getSeatsByRoom(Long roomId) {
        List<Seat> seats = seatDomainService.getSeatsByRoom(roomId);
        return DisplaySeatDto.from(seats);
    }

    @Override
    public List<DisplaySeatDto> getAvailableSeatsByRoom(Long roomId) {
        List<Seat> seats = seatDomainService.getAvailableSeatsByRoom(roomId);
        return DisplaySeatDto.from(seats);
    }

    @Override
    public long countAvailableSeats(Long roomId) {
        return seatDomainService.countAvailableSeats(roomId);
    }


    @Override
    public DisplaySeatDto createSeat(CreateSeatDto dto) {

        UserWrapper user = null;
        if (dto.userId() != null) {
            user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Room room = null;
        if (dto.roomId() != null) {
            room = roomRepository.findById(dto.roomId())
                    .orElseThrow(() -> new RuntimeException("Room not found"));
        }

        Seat seat = new Seat(
                dto.seatNumber(),
                dto.isTaken(),
                user,
                room
        );

        Seat saved = seatDomainService.saveSeat(seat);

        return DisplaySeatDto.from(saved);
    }


    @Override
    public DisplaySeatDto getSeat(Long id) {
        Seat seat = seatDomainService.getSeatEntityById(id);
        return DisplaySeatDto.from(seat);
    }

    @Override
    public void deleteSeat(Long id) {
        Seat seat = seatDomainService.getSeatEntityById(id);
        seatDomainService.deleteSeatEntity(id);
    }

    @Override
    public DisplaySeatDto toggleSeatStatus(Long id) {
        Seat seat = seatDomainService.getSeatEntityById(id);
        seat.setTaken(!seat.isTaken());
        Seat saved = seatDomainService.saveSeat(seat);
        return DisplaySeatDto.from(saved);
    }

    @Override
    public DisplaySeatDto changeSeatUser(Long seatId, Long newUserId) {
        Seat seat = seatDomainService.getSeatEntityById(seatId);
        UserWrapper newUser = userDomainService.getUserEntityById(newUserId);

        seat.setUser(newUser);
        Seat updatedSeat = seatDomainService.saveSeat(seat);

        return DisplaySeatDto.from(updatedSeat);
    }


    @Override
    public DisplaySeatDto updateSeat(Long seatId, UpdateSeatDto dto) {
        Seat seat = seatDomainService.getSeatEntityById(seatId);

        if (dto.isTaken() != null) {
            seat.setTaken(dto.isTaken());
        }

        if (dto.userId() != null) {
            UserWrapper user = userDomainService.getUserEntityById(dto.userId());
            seat.setUser(user);
        }

        Seat updated = seatDomainService.saveSeat(seat);
        return DisplaySeatDto.from(updated);
    }

    @Override
    public boolean isSeatAvailable(Long seatId) {
        return seatDomainService.isSeatAvailable(seatId);
    }

    @Override
    public void reserveSeat(Long seatId, Long userId) {
        seatDomainService.reserveSeat(seatId, userId);
    }

    @Override
    public void releaseSeat(Long seatId) {
        seatDomainService.releaseSeat(seatId);
    }
}


