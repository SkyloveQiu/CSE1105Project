package nl.tudelft.oopp.group43.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.RoomPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomPageController {

    @FXML
    private ChoiceBox<String> fromTime;
    @FXML
    private ChoiceBox<String> untilTime;
    @FXML
    private DatePicker date;
    @FXML
    private TextField searchBar;
    @FXML
    private AnchorPane root;

    @FXML
    private void fromHourSelected(MouseEvent event) {
        String untilHour = untilTime.getValue();

        if (untilHour != null) {
            int i = 0;
            String time = "00:00";
            while(!untilHour.equals(time)) {
                i++;
                if (i < 10) {
                    time = "0" + i + ":00";
                } else {
                    time = i + ":00";
                }
            }

            ArrayList<String> hours = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (j < 10) {
                    hours.add("0" + Integer.toString(j) + ":00");
                } else {
                    hours.add(Integer.toString(j) + ":00");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            fromTime.setItems(list);

            RoomPageContent.setHoursFrom("");
        }
    }

    @FXML
    private void untilHourSelected(MouseEvent event) {
        String fromHour = fromTime.getValue();

        if (fromHour != null) {
            int i = 0;
            String time = "00:00";
            while(!fromHour.equals(time)) {
                i++;
                if (i < 10) {
                    time = "0" + i + ":00";
                } else {
                   time = i + ":00";
                }
            }

            ArrayList<String> hours = new ArrayList<>();
            for (i += 1; i < 24; i++) {
                if (i < 10) {
                    hours.add("0" + Integer.toString(i) + ":00");
                } else {
                    hours.add(Integer.toString(i) + ":00");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            untilTime.setItems(list);

            RoomPageContent.setHoursTil("");
        }
    }

    @FXML
    private void searchRooms(MouseEvent event) {
        String searchQuery = searchBar.getText();
        if (searchQuery != null) {
            System.out.println("Searching in rooms for: " + searchQuery);

            JSONArray rooms = RoomPageContent.getDatabaseRooms();
            ArrayList<JSONObject> newRooms = new ArrayList<>();
            for (int i = 0; i < rooms.size(); i++) {
                if ( StringChecker.containsIgnoreCase(searchQuery, ((String) ((JSONObject) rooms.get(i)).get("room_name"))) || StringChecker.containsIgnoreCase(searchQuery, ((String) ((JSONObject) ((JSONObject) rooms.get(i)).get("building")).get("building_name")))) {
                    System.out.println("room: " + (String) ((JSONObject) rooms.get(i)).get("room_name") + " building: " + (String) ((JSONObject) ((JSONObject) rooms.get(i)).get("building")).get("building_name") + " is correct");
                    newRooms.add((JSONObject) rooms.get(i));
                }
            }

            RoomPageContent.setSelectedRooms(newRooms);
            RoomPageContent.addRooms();
        }
    }

    @FXML
    private void confirmDateTime(ActionEvent event) {
        if (!RoomPageContent.getHoursFrom().equals("") && !RoomPageContent.getHoursTil().equals("") && RoomPageContent.getDate() != null) {
            ReservationConfig.resetSelectedHours();

            LocalDate localDate = date.getValue();

            String month = Integer.toString(localDate.getMonthValue());
            String day = Integer.toString(localDate.getDayOfMonth());
            if (localDate.getMonthValue() < 10) {
                month = "0" + localDate.getMonthValue();
            }
            if (localDate.getDayOfMonth() < 10) {
                day = "0" + localDate.getDayOfMonth();
            }
            String dateString = localDate.getYear() + "-" + month + "-" + day;

            String bHour = dateString + "-" + fromTime.getValue().substring(0, 2);
            String eHour = dateString + "-" + untilTime.getValue().substring(0, 2);
            //System.out.println(bHour + " - " + eHour);
            int hoursBetween = Integer.parseInt(untilTime.getValue().substring(0, 2)) - Integer.parseInt(fromTime.getValue().substring(0, 2));
            //System.out.println(hoursBetween);

            for(int i = 0; i <= hoursBetween; i++) {
                int offset = i + Integer.parseInt(fromTime.getValue().substring(0, 2));
                String hourOffset = "";
                if (offset < 10) {
                    hourOffset = "-0" + offset;
                } else {
                    hourOffset = "-" + offset;
                }
                String hour = dateString + hourOffset;
                ReservationConfig.addHour(hour);
            }


            /*
            ===========================================
             */


            ArrayList selectedHours = ReservationConfig.getSelectedHours();

            String response = "";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
            formatter.withZone(ZoneId.of("UTC"));
            for (int i = 0; i < selectedHours.size(); i++) {
                LocalDateTime startDate = LocalDateTime.parse((String) selectedHours.get(i), formatter);
                LocalDateTime endDate = startDate.plusHours(1);

                String startTime = startDate.toString() + ":00.000+0000";
                String endTime = endDate.toString() + ":00.000+0000";
                System.out.println(startTime);
                response = ServerCommunication.reserveRoomForHour(startTime, endTime);

                System.out.println(response);
            }


            /*
            ===========================================
             */


            root.getChildren().remove(6);
            RoomPageContent.addRooms();
            //dateTimeSelect.setVisible(false);
        }
    }

}
