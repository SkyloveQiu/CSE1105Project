package nl.tudelft.oopp.group43.content;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ExceptionsPageContent {

    private static Scene scene;
    private static TextField time;
    private static RadioButton differentButton;
    private static ArrayList <String> buildingName;
    private static ArrayList <Long> buildingNumber;


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
     //   addCheckBoxes();
        time = (TextField) scene.lookup("#time");
        time.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                if (newValue.length() > 11) {
                    ((StringProperty) observable).setValue(oldValue);
                    return;
                }
                if (newValue.charAt(newValue.length() - 1) < 48 || newValue.charAt(newValue.length() - 1) > 57) {
                    if (newValue.charAt(newValue.length() - 1) != ':' && newValue.length() - 1 != 2 && newValue.length() - 1 != 8 && newValue.charAt(newValue.length() - 1) != '-' && newValue.length() - 1 != 5) {
                        String comp = "closed";
                        if (newValue.length() > comp.length() || newValue.charAt(newValue.length() - 1) != comp.charAt(newValue.length() - 1)) {
                            ((StringProperty) observable).setValue(oldValue);
                        }
                    }
                }
            }
        });

        differentButton.selectedProperty().addListener((observable, oldValue, newValue) -> toggleCheckBoxes(newValue));
    }

    private static void addBuildings() {
        ChoiceBox<String> buildingBox = (ChoiceBox<String>) scene.lookup("#buildings");
        JSONParser json = new JSONParser();
        try {
            JSONArray array = (JSONArray) json.parse(ServerCommunication.getBuilding());
            ArrayList<String> buildings = new ArrayList<String>();
            buildingName = new ArrayList<>();
            buildingNumber = new ArrayList<>();
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                String name = (String) jsonObject.get("building_name");
                Long number = (Long) jsonObject.get("building_number");
                buildings.add(name);
                buildingName.add(name);
                buildingNumber.add(number);
            }

            buildingBox.setItems(FXCollections.observableArrayList(buildings));
            buildingBox.getSelectionModel().selectFirst();



        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

   private static void toggleCheckBoxes(Boolean newValue) {
        if (newValue) {
            time.setVisible(true);
        } else {
            time.setVisible(false);
        }
    }

    private static void addCheckBoxes() {

        ArrayList<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + i + ":00");
            } else {
                hours.add(i + ":00");
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(hours);



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

    private static Long getBuilding(String building)
    {
        int i;
        Long nr = Long.valueOf(-1);
        for(i = 0; i <buildingName.size(); i++)
        {
            if(building.equals(buildingName.get(i)) == true) {
               nr = buildingNumber.get(i);
               break;
            }
        }
        return  nr;

    }
}
