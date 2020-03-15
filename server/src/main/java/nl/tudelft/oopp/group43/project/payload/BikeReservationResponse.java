package nl.tudelft.oopp.group43.project.payload;

import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.BikeReservation;

public class BikeReservationResponse {
    Bike bike;
    BikeReservation bikeReservation;
    String message;
    int status;

    public BikeReservationResponse(Bike bike, BikeReservation bikeReservation, String message, int status) {
        this.bike = bike;
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

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
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
