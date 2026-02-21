package mk.ukim.finki.elibrary.server.service.backend.application;

import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;

import java.util.List;

public interface SeatApplicationService {
    DisplaySeatDto createSeat(CreateSeatDto dto);
    DisplaySeatDto getSeat(Long id);
    void deleteSeat(Long id);
    DisplaySeatDto toggleSeatStatus(Long id);
    DisplaySeatDto changeSeatUser(Long seatId, Long newUserId);
    List<DisplaySeatDto> getAllSeats();
    DisplaySeatDto updateSeat(Long seatId, UpdateSeatDto dto);
    boolean isSeatAvailable(Long seatId);
    void reserveSeat(Long seatId, Long userId);
    void releaseSeat(Long seatId);
    List<DisplaySeatDto> getSeatsByRoom(Long roomId);
    List<DisplaySeatDto> getAvailableSeatsByRoom(Long roomId);
    long countAvailableSeats(Long roomId);

}
