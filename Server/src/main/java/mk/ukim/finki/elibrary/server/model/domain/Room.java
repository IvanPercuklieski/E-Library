package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int numSeats;

    @OneToMany(mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Seat> seats;

    public Room(String name, String location, int numSeats, List<Seat> seats) {
        this.name = name;
        this.location = location;
        this.numSeats = numSeats;
        this.seats = seats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public long getAvailableSeatsCount() {
        if (seats == null) return 0;
        return seats.stream()
                .filter(seat -> !seat.isTaken())
                .count();
    }

    public boolean hasFreeSeats() {
        return getAvailableSeatsCount() > 0;
    }

    public boolean hasReservedSeats() {
        if (seats == null) return false;
        return seats.stream()
                .anyMatch(Seat::isTaken);
    }

}
