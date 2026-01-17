package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.DisplayRoomDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.model.domain.Room;
import mk.ukim.finki.elibrary.server.service.backend.application.RoomApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomApplicationService roomService;

    public RoomController(RoomApplicationService roomService) {
        this.roomService = roomService;
    }


    @GetMapping
    public List<DisplayRoomDto> getAllRooms() {
        return DisplayRoomDto.from(roomService.getAllRooms());
    }


    @GetMapping("/{roomId}")
    public DisplayRoomDto getRoom(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return DisplayRoomDto.from(room);
    }


    @GetMapping("/{roomId}/seats")
    public List<Seat> getSeatsInRoom(@PathVariable Long roomId) {
        return roomService.getSeatsInRoom(roomId);
    }


    @GetMapping("/seats/{seatId}/availability")
    public Map<String, Boolean> isSeatAvailable(@PathVariable Long seatId) {
        boolean available = roomService.isSeatAvailable(seatId);
        return Map.of("available", available);
    }


    @PostMapping("/seats/{seatId}/reserve")
    public ResponseEntity<String> reserveSeat(@PathVariable Long seatId,
                                              @RequestParam Long userId) {
        roomService.reserveSeat(seatId, userId);
        return ResponseEntity.ok("Seat reserved successfully");
    }


    @PostMapping("/seats/{seatId}/release")
    public ResponseEntity<String> releaseSeat(@PathVariable Long seatId) {
        roomService.releaseSeat(seatId);
        return ResponseEntity.ok("Seat released successfully");
    }
}

