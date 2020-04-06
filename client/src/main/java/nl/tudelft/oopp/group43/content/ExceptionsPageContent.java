package nl.tudelft.oopp.group43.content;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ExceptionsPageContent {

    private static Scene scene;
    private static TextField time;
    private static ArrayList<String> buildingName;
    private static ArrayList<Long> buildingNumber;


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
        Label timeCheck = (Label) scene.lookup("#timeCheck");
        time = (TextField) scene.lookup("#time");
        time.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                if (newValue.length() > 11) {
                    ((StringProperty) observable).setValue(oldValue);
                    return;
                }
                if (newValue.length() == 3 || newValue.length() == 6 || newValue.length() == 9) {
                    if (newValue.length() == 6 && newValue.charAt(newValue.length() - 1) != '-') {
                        ((StringProperty) observable).setValue(oldValue);
                        return;
                    }
                    if (newValue.length() != 6 && newValue.charAt(newValue.length() - 1) != ':') {
                        ((StringProperty) observable).setValue(oldValue);
                        return;
                    }
                } else if (newValue.charAt(newValue.length() - 1) < 48 || newValue.charAt(newValue.length() - 1) > 57) {
                    ((StringProperty) observable).setValue(oldValue);
                } else {
                    timeCheck.setText("This field is not complete!");

                }
                if (newValue.length() == 11) {
                    String[] array = newValue.split("-");
                    int value = array[0].compareTo(array[1]);
                    boolean ok = checkHour(array[0], timeCheck);
                    ok = ok && checkHour(array[1], timeCheck);
                    if (ok == false) {
                        return;
                    }
                    if (value <= 0) {
                        timeCheck.setText("");
                        return;
                    } else {
                        timeCheck.setText("The starting hour must be smaller than the finish hour!");
                        return;
                    }

                }
            }
        });

        differentButton.selectedProperty().addListener((observable, oldValue, newValue) -> toggleCheckBoxes(newValue));
    }

    /**
     * Adds buildings in the choice box.
     */
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
        Label message = (Label) scene.lookup("#message");
        Label timeCheck = (Label) scene.lookup("#timeCheck");

        if (newValue) {
            time.setVisible(true);
            message.setVisible(true);
            timeCheck.setVisible(true);


        } else {
            time.setVisible(false);
            message.setVisible(false);
            timeCheck.setVisible(false);
        }
    }

    /**
     * Adds the date picker.
     */
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

    /**
     * Gets the number of the chosen building.
     * @param building - String which represents the buildings.
     * @return - Long value which represents the building number.
     */
    public static Long getBuilding(String building) {
        int i;
        Long nr = Long.valueOf(-1);
        for (i = 0; i < buildingName.size(); i++) {
            if (building.equals(buildingName.get(i)) == true) {
                nr = buildingNumber.get(i);
                break;
            }
        }
        return nr;
    }

    /**
     * Checks if a String represent an hour (HH:mm format).
     * @param s    - String which should be checked.
     * @param text - Label where you should show the warning messages.
     * @return true if String s represents an hour, false otherwise.
     */
    private static boolean checkHour(String s, Label text) {

        boolean ok = true;
        String[] array = s.split(":");
        int value = array[0].compareTo("00");
        if (value < 0) {
            ok = false;
        } else {
            value = array[0].compareTo("23");
            if (value > 0) {
                ok = false;
            }
        }
        value = array[1].compareTo("00");
        if (value < 0) {
            ok = false;
        } else {
            value = array[1].compareTo("59");
            if (value > 0) {
                ok = false;
            }
        }

        if (ok == false) {
            text.setText("Something goes wrong with writing the opening/closing hour!!");
        } else {
            text.setText("");
        }
        return ok;
    }
}
