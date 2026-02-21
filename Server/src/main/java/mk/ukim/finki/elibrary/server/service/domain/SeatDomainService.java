package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import java.util.List;

public interface SeatDomainService {
    List<Seat> getAllSeats();

    List<Seat> getSeatsByRoom(Long roomId);

    Seat saveSeat(Seat seat);

    Seat getSeatEntityById(Long id);

    void deleteSeatEntity(Long id);

    void saveAllSeats(List<Seat> seats);

    void deleteSeats(List<Seat> seats);

    List<Seat> getAvailableSeatsByRoom(Long roomId);

    long countAvailableSeats(Long roomId);

    boolean isSeatAvailable(Long seatId);

    void reserveSeat(Long seatId, Long userId);

    void releaseSeat(Long seatId);


}
