package mk.ukim.finki.elibrary.server.web.controllers;



import mk.ukim.finki.elibrary.server.dto.ChangeSeatUserDto;
import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateSeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.SeatApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.RoomDomainService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatApplicationService seatAppService;
    private final SeatDomainService seatDomainService;


    public SeatController(SeatApplicationService seatAppService, SeatDomainService seatDomainService) {
        this.seatAppService = seatAppService;
        this.seatDomainService = seatDomainService;

    }


    @GetMapping
    public List<DisplaySeatDto> getAllSeats() {
        List<Seat> seats = seatDomainService.getAllSeats();
        return DisplaySeatDto.from(seats);
    }



    @GetMapping("seat/{id}")
    public DisplaySeatDto getSeat(@PathVariable Long id) {
        return seatAppService.getSeat(id);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DisplaySeatDto createSeat(@RequestBody(required = false) CreateSeatDto seatDto) {
        if (seatDto == null) {
            throw new IllegalArgumentException("Seat data is required");
        }
        return seatAppService.createSeat(seatDto);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("remove/{id}")
    public void removeSeat(@PathVariable Long id) {
        seatAppService.deleteSeat(id);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/toggle")
    public DisplaySeatDto toggleSeatStatus(@PathVariable Long id) {
        return seatAppService.toggleSeatStatus(id);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{seatId}/change-user")
    public DisplaySeatDto changeSeatUser(@PathVariable Long seatId,
                                         @RequestBody ChangeSeatUserDto dto) {
        return seatAppService.changeSeatUser(seatId, dto.newUserId());
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public DisplaySeatDto updateSeat(@PathVariable Long id,
                                     @RequestBody UpdateSeatDto dto) {
        return seatAppService.updateSeat(id, dto);
    }

    @GetMapping("/seats/{seatId}/availability")
    public Map<String, Boolean> isSeatAvailable(@PathVariable Long seatId) {
        boolean available = seatDomainService.isSeatAvailable(seatId);
        return Map.of("available", available);
    }


    @PostMapping("/seats/{seatId}/reserve")
    public ResponseEntity<String> reserveSeat(@PathVariable Long seatId,
                                              @RequestParam Long userId) {
        seatDomainService.reserveSeat(seatId, userId);
        return ResponseEntity.ok("Seat reserved successfully");
    }


    @PostMapping("/seats/{seatId}/release")
    public ResponseEntity<String> releaseSeat(@PathVariable Long seatId) {
        seatDomainService.releaseSeat(seatId);
        return ResponseEntity.ok("Seat released successfully");
    }

    // сите seats во room
    @GetMapping("/room/{roomId}")
    public List<DisplaySeatDto> getSeatsByRoom(@PathVariable Long roomId) {
        return seatAppService.getSeatsByRoom(roomId);
    }

    // available seats
    @GetMapping("/room/{roomId}/available")
    public List<DisplaySeatDto> getAvailableSeats(@PathVariable Long roomId) {
        return seatAppService.getAvailableSeatsByRoom(roomId);
    }

    // count available seats
    @GetMapping("/room/{roomId}/available-count")
    public long countAvailableSeats(@PathVariable Long roomId) {
        return seatAppService.countAvailableSeats(roomId);
    }

}
