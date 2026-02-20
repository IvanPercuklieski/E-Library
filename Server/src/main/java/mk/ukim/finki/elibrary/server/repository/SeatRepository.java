package mk.ukim.finki.elibrary.server.repository;

import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByRoomId(Long roomId);
    Optional<Seat> findByRoomAndSeatNumber(Room room, int seatNumber);
    List<Seat> findByRoomIdAndIsTakenFalse(Long roomId);
    
    boolean existsSeatByUser_Id(Long userId);
}
