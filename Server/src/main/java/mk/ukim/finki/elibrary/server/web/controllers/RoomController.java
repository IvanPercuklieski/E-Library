package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.CreateRoomDto;
import mk.ukim.finki.elibrary.server.dto.DisplayRoomDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateRoomDto;
import mk.ukim.finki.elibrary.server.model.domain.Seat;
import mk.ukim.finki.elibrary.server.service.backend.application.RoomApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomApplicationService roomService;

    public RoomController(RoomApplicationService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/{roomId}/seats")
    public List<Seat> getSeatsInRoom(@PathVariable Long roomId) {
        return roomService.getSeatsInRoom(roomId);
    }


    // CREATE room
    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create-room")
    public ResponseEntity<DisplayRoomDto> createRoom(@RequestBody CreateRoomDto dto) {
        DisplayRoomDto created = roomService.createRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // UPDATE room
    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update-room/{id}")
    public ResponseEntity<DisplayRoomDto> updateRoom(@PathVariable Long id,
                                                     @RequestBody UpdateRoomDto dto) {
        DisplayRoomDto updated = roomService.updateRoom(id, dto);
        return ResponseEntity.ok(updated);
    }

    // GET room by ID
    @GetMapping("room/{id}")
    public ResponseEntity<DisplayRoomDto> getRoom(@PathVariable Long id) {
        DisplayRoomDto room = roomService.getRoom(id);
        return ResponseEntity.ok(room);
    }

    // GET all rooms
    @GetMapping
    public ResponseEntity<List<DisplayRoomDto>> getAllRooms() {
        List<DisplayRoomDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException ex) {
            //  PRECONDITION_FAILED (412)
            throw ex;
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/available")
    public List<DisplayRoomDto> getRoomsWithAvailableSeats() {
        return roomService.getRoomsWithAvailableSeats();
    }

}

