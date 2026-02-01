package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int numSeats;

    @OneToMany(mappedBy = "room")
    private List<Seat> seats;

    public Room(Long id, String name, String location, int numSeats, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.numSeats = numSeats;
        this.seats = seats;
    }

    public Room(String name, String location, int numSeats, List<Seat> seats) {
        this.name = name;
        this.location = location;
        this.numSeats = numSeats;
        this.seats = seats;
    }
}
