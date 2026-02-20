package mk.ukim.finki.elibrary.server.service.backend.application.impl;

import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.repository.RoomRepository;
import mk.ukim.finki.elibrary.server.service.backend.application.SeatApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import org.springframework.stereotype.Service;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;

import java.util.List;

@Service
public class SeatApplicationServiceImpl implements SeatApplicationService {

    private final SeatDomainService seatDomainService;
    private final UserWrapperRepository userRepository;
    private final RoomRepository roomRepository;

    public SeatApplicationServiceImpl(
            SeatDomainService seatDomainService,
            UserWrapperRepository userRepository,
            RoomRepository roomRepository
    ) {
        this.seatDomainService = seatDomainService;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatDomainService.getAllSeats();
    }

    @Override
    public List<Seat> getSeatsByRoom(Long roomId) {
        return seatDomainService.getSeatsByRoom(roomId);
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
}


