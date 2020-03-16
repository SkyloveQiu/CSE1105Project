package nl.tudelft.oopp.group43.project.payload;

import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.BikeReservation;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.Room;

public class ReservationResponse {
    Room room;
    Reservation reservation;
    String message;
    int status;

    /**
     * Creates a new ReservationResponse.
     * @param room represents the Room.
     * @param reservation represents the Reservation.
     * @param message represents the message.
     * @param status represents the status.
     */
    public ReservationResponse(Room room, Reservation reservation, String message, int status) {
        this.room = room;
        this.reservation = reservation;
        this.message = message;
        this.status = status;
    }

    public ReservationResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
