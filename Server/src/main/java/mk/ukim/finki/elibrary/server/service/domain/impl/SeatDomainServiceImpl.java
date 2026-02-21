package mk.ukim.finki.elibrary.server.service.domain.impl;

import mk.ukim.finki.elibrary.server.model.domain.UserWrapper;
import mk.ukim.finki.elibrary.server.model.exceptions.SeatAlreadyTakenException;
import mk.ukim.finki.elibrary.server.model.exceptions.SeatNotFoundException;
import mk.ukim.finki.elibrary.server.model.exceptions.UserWrapperNotFoundException;
import mk.ukim.finki.elibrary.server.repository.SeatRepository;
import mk.ukim.finki.elibrary.server.repository.UserWrapperRepository;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatDomainServiceImpl implements SeatDomainService {
    private final SeatRepository seatRepository;
    private final UserWrapperRepository userWrapperRepository;

    public SeatDomainServiceImpl(SeatRepository seatRepository, UserWrapperRepository userWrapperRepository){
        this.seatRepository = seatRepository;
        this.userWrapperRepository = userWrapperRepository;
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

    @Override
    public void saveAllSeats(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    @Override
    public void deleteSeats(List<Seat> seats) {
        seatRepository.deleteAll(seats);
    }

    @Override
    public List<Seat> getAvailableSeatsByRoom(Long roomId) {
        return seatRepository.findByRoomIdAndIsTakenFalse(roomId);
    }

    @Override
    public long countAvailableSeats(Long roomId) {
        return seatRepository.countByRoomIdAndIsTakenFalse(roomId);
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

        UserWrapper user = userWrapperRepository.findById(userId)
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
