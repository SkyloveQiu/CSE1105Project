package nl.tudelft.oopp.group43.content;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ExceptionsPageContent {

    private static Scene scene;
    private static ChoiceBox<String> fromTime;
    private static ChoiceBox<String> untilTime;
    private static RadioButton differentButton;

    /**
     * Adds the content of the page.
     * @param currentScene the current scene of the page.
     */

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addDatePicker();
        addBuildings();
        ToggleGroup radioButtons = new ToggleGroup();
        RadioButton closedButton = (RadioButton) scene.lookup("#closed");
        RadioButton differentButton = (RadioButton) scene.lookup("#different");
        closedButton.setToggleGroup(radioButtons);
        differentButton.setToggleGroup(radioButtons);
        addCheckBoxes();
        differentButton.selectedProperty().addListener((observable, oldValue, newValue) -> toggleCheckBoxes(newValue));
    }

    private static void addBuildings() {
        ChoiceBox<String> buildingBox = (ChoiceBox<String>) scene.lookup("#buildings");
        JSONParser json = new JSONParser();
        try {
            JSONArray array = (JSONArray) json.parse(ServerCommunication.getBuilding());
            ArrayList<String> buildings = new ArrayList<String>();
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                buildings.add((String) jsonObject.get("building_name"));
            }

            buildingBox.setItems(FXCollections.observableArrayList(buildings));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void toggleCheckBoxes(Boolean newValue) {
        if (newValue) {
            fromTime.setVisible(true);
            untilTime.setVisible(true);
        } else {
            fromTime.setVisible(false);
            untilTime.setVisible(false);
        }
    }

    private static void addCheckBoxes() {
        fromTime = (ChoiceBox<String>) scene.lookup("#fromTime");
        untilTime = (ChoiceBox<String>) scene.lookup("#untilTime");
        ArrayList<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + i + ":00");
            } else {
                hours.add(i + ":00");
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(hours);
        fromTime.setItems(list);
        untilTime.setItems(list);
    }


    private static void addDatePicker() {
        DatePicker startingDatePicker = (DatePicker) scene.lookup("#exceptionsDatePicker");
        startingDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
}
