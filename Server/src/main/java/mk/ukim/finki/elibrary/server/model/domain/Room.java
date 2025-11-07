package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
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


    public Room() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
