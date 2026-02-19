package mk.ukim.finki.elibrary.server.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seats")
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


    public Seat(int seatNumber, boolean isTaken, UserWrapper user, Room room) {
        this.seatNumber = seatNumber;
        this.isTaken = isTaken;
        this.user = user;
        this.room = room;
    }
}
