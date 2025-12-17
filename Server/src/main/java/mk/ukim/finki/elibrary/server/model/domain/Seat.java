package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber;
    private boolean isTaken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserWrapper user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Seat(Long id, int seatNumber, boolean isTaken, UserWrapper user, Room room) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.isTaken = isTaken;
        this.user = user;
        this.room = room;
    }

    public Seat(int seatNumber, boolean isTaken, UserWrapper user, Room room) {
        this.seatNumber = seatNumber;
        this.isTaken = isTaken;
        this.user = user;
        this.room = room;
    }

    public Seat() {

    }

    public Long getId() {
        return id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public UserWrapper getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public void setUser(UserWrapper user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
