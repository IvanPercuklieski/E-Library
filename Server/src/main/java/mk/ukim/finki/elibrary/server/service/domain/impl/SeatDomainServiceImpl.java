package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.repository.SeatRepository;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatDomainServiceImpl implements SeatDomainService {
    private final SeatRepository seatRepository;

    public SeatDomainServiceImpl(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    @Override
    public List<Seat> getSeatsByRoom(Long roomId) {
        return seatRepository.findByRoomId(roomId);
    }

    @Override
    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Seat getSeatEntityById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
    }

    @Override
    public void deleteSeatEntity(Long id) {
        seatRepository.deleteById(id);
    }

}
