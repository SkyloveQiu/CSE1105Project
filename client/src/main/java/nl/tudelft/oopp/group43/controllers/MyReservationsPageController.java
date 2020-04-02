package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

public class MyReservationsPageController {
    public static void editReservation(Long i) {
        System.out.println("editReservation with reservation id " + i);

    }

    public static void deleteReservation(Long i) {
        System.out.println("deleteReservation with reservation id " + i);
        String reservationId = Long.toString(i);
        System.out.println(ServerCommunication.sendDeleteReservation(reservationId));
        System.out.println("deleted");
    }
}
