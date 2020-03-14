package nl.tudelft.oopp.group43.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.group43.content.RoomPageContent;

import java.util.ArrayList;

public class RoomPageController {

    @FXML
    private ChoiceBox<String> fromTime;
    @FXML
    private ChoiceBox<String> untilTime;

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

}
