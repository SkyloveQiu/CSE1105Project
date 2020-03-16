package nl.tudelft.oopp.group43.project.payload;

import nl.tudelft.oopp.group43.project.models.BikeReservation;

public class BikeReservationResponse {
    BikeReservation bikeReservation;
    String message;
    int status;

    /**
     * init the bike reservation.
     * @param bikeReservation the bike reservation.
     * @param message the message.
     * @param status the status of reservation.
     */
    public BikeReservationResponse(BikeReservation bikeReservation, String message, int status) {
        this.bikeReservation = bikeReservation;
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public BikeReservation getBikeReservation() {
        return bikeReservation;
    }

    public void setBikeReservation(BikeReservation bikeReservation) {
        this.bikeReservation = bikeReservation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
