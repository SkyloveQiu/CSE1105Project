package nl.tudelft.oopp.group43.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

public class MyReservationsPageController {
    public static void editReservation(Long i) {
        System.out.println("editReservation with reservation id " + i);

    }

    /**
     * If you press the delete reservation button, the method will warn you and delete the reservation.
     *
     * @param resId - the reservation id
     */

    public static void deleteReservation(Long resId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete this reservation?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                System.out.println("deleteReservation with reservation id " + resId);
                String reservationId = Long.toString(resId);
                //System.out.println(ServerCommunication.sendDeleteReservation(reservationId));
                System.out.println("deleted");
            }
            else{
                System.out.println("nothing happened");
            }
        });
    }
}
