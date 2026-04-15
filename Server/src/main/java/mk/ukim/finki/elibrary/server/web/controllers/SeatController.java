package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.ChangeSeatUserDto;
import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateSeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.SeatApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/seats")
public class SeatController {

    private final SeatApplicationService seatAppService;
    private final SeatDomainService seatDomainService;

    public SeatController(SeatApplicationService seatAppService,
                          SeatDomainService seatDomainService) {
        this.seatAppService = seatAppService;
        this.seatDomainService = seatDomainService;
    }

    @GetMapping
    public List<DisplaySeatDto> getAllSeats() {
        List<Seat> seats = seatDomainService.getAllSeats();
        return DisplaySeatDto.from(seats);
    }

    @GetMapping("/{id}")
    public DisplaySeatDto getSeat(@PathVariable Long id) {
        return seatAppService.getSeat(id);
    }

    @PostMapping
    public DisplaySeatDto createSeat(@RequestBody CreateSeatDto seatDto) {
        if (seatDto == null) {
            throw new IllegalArgumentException("Seat data is required");
        }
        return seatAppService.createSeat(seatDto);
    }

    @DeleteMapping("/{id}")
    public void removeSeat(@PathVariable Long id) {
        seatAppService.deleteSeat(id);
    }

    @PutMapping("/{id}")
    public DisplaySeatDto updateSeat(@PathVariable Long id,
                                     @RequestBody UpdateSeatDto dto) {
        return seatAppService.updateSeat(id, dto);
    }

    @PatchMapping("/{id}/toggle")
    public DisplaySeatDto toggleSeatStatus(@PathVariable Long id) {
        return seatAppService.toggleSeatStatus(id);
    }

    @GetMapping("/{seatId}/availability")
    public Map<String, Boolean> isSeatAvailable(@PathVariable Long seatId) {
        boolean available = seatDomainService.isSeatAvailable(seatId);
        return Map.of("available", available);
    }

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<String> reserveSeat(@PathVariable Long seatId,
                                              @RequestParam Long userId) {
        seatDomainService.reserveSeat(seatId, userId);
        return ResponseEntity.ok("Seat reserved successfully");
    }

    @PostMapping("/{seatId}/release")
    public ResponseEntity<String> releaseSeat(@PathVariable Long seatId) {
        seatDomainService.releaseSeat(seatId);
        return ResponseEntity.ok("Seat released successfully");
    }

    @GetMapping("/room/{roomId}")
    public List<DisplaySeatDto> getSeatsByRoom(@PathVariable Long roomId) {
        return seatAppService.getSeatsByRoom(roomId);
    }

    @GetMapping("/room/{roomId}/available")
    public List<DisplaySeatDto> getAvailableSeats(@PathVariable Long roomId) {
        return seatAppService.getAvailableSeatsByRoom(roomId);
    }

    @GetMapping("/room/{roomId}/available-count")
    public long countAvailableSeats(@PathVariable Long roomId) {
        return seatAppService.countAvailableSeats(roomId);
    }
}