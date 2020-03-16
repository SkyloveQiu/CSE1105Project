package nl.tudelft.oopp.group43.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.content.RoomPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class RoomPageController {

    @FXML
    private ChoiceBox<String> fromTime;
    @FXML
    private ChoiceBox<String> untilTime;
    @FXML
    private TextField searchBar;
    @FXML
    private AnchorPane dateTimeSelect;

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
            dateTimeSelect.setVisible(false);
            RoomPageContent.addRooms();
        }
    }

}
