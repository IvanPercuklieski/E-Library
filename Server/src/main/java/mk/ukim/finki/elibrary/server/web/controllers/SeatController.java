package mk.ukim.finki.elibrary.server.web.controllers;



import mk.ukim.finki.elibrary.server.dto.CreateSeatDto;
import mk.ukim.finki.elibrary.server.dto.DisplaySeatDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.SeatApplicationService;
import mk.ukim.finki.elibrary.server.service.domain.SeatDomainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    @GetMapping("/{id}")
    public DisplaySeatDto getSeat(@PathVariable Long id) {
        return seatAppService.getSeat(id);
    }


    @PostMapping
    public DisplaySeatDto createSeat(@RequestBody(required = false) CreateSeatDto seatDto) {
        if (seatDto == null) {
            throw new IllegalArgumentException("Seat data is required");
        }
        return seatAppService.createSeat(seatDto);
    }


    @DeleteMapping("/{id}")
    public void removeSeat(@PathVariable Long id) {
        seatAppService.deleteSeat(id);
    }


    @PatchMapping("/{id}/toggle")
    public DisplaySeatDto toggleSeatStatus(@PathVariable Long id) {
        return seatAppService.toggleSeatStatus(id);
    }
}
