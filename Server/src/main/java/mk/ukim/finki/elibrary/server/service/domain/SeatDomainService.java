package mk.ukim.finki.elibrary.server.service.domain;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import java.util.List;

public interface SeatDomainService {
    List<Seat> getAllSeats();

    List<Seat> getSeatsByRoom(Long roomId);

    Seat saveSeat(Seat seat);

    Seat getSeatEntityById(Long id);

    void deleteSeatEntity(Long id);

}
