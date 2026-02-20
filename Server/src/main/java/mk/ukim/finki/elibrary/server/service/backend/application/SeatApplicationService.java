package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;

import java.util.List;

public interface SeatApplicationService {
    List<Seat> getAllSeats();
    List<Seat> getSeatsByRoom(Long roomId);
    DisplaySeatDto createSeat(CreateSeatDto dto);
    DisplaySeatDto getSeat(Long id);
    void deleteSeat(Long id);
    DisplaySeatDto toggleSeatStatus(Long id);
}
