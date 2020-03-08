package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ReservationPageController {

    @FXML
    private Label progress;

    @FXML
    private void confirmReservation(ActionEvent e) {
        ArrayList selectedHours = ReservationConfig.getSelectedHours();

        progress.setText("The room is being reserved for the selected timeslots,\nPlease wait...");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
        formatter.withZone(ZoneId.of("UTC"));
        for(int i = 0; i < selectedHours.size(); i++) {
            LocalDateTime startDate = LocalDateTime.parse((String) selectedHours.get(i), formatter);
            LocalDateTime endDate = startDate.plusHours(1);

            String startTime = startDate.toString() + ":00.000+0000";
            String endTime = endDate.toString() + ":00.000+0000";
            System.out.println(startTime);
            ServerCommunication.reserveRoomForHour(startTime, endTime);
        }
    }
}
