package nl.tudelft.oopp.group43.controllers;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.ExceptionsPageContent;

import org.json.simple.JSONObject;

public class ExceptionsPageController {

    @FXML
    private DatePicker exceptionsDatePicker;
    @FXML
    private Label dateCheck;
    @FXML
    private ChoiceBox<String> buildings;
    @FXML
    private Label timeCheck;
    @FXML
    private RadioButton closed;
    @FXML
    private RadioButton different;
    @FXML
    private Label choiceCheck;
    @FXML
    private TextField time;

    /**
     * Checks if the Date field is completed.
     * @return true if the Date field is completed, false otherwise.
     */
    private boolean checkDate() {
        LocalDate date = exceptionsDatePicker.getValue();
        if (date == null) {
            dateCheck.setText("You must select an exception date");
            return false;
        } else {
            dateCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the user made a choice.
     * @return true if the user made a choice, false otherwise.
     */
    private boolean checkChoice() {
        if (!closed.isSelected() && !different.isSelected()) {
            choiceCheck.setText("You must choose one of these options");
            return false;
        } else {
            choiceCheck.setText("");
            return true;
        }
    }

    /**
     * Sends the input to the server regarding the exception.
     * @param event - the Button is pressed.
     */
    @FXML
    @SuppressWarnings("unchecked")
    public void confirmException(ActionEvent event) {

        boolean ok = checkDate();
        ok = checkChoice() && ok;
        if (ok == true) {
            String hours = "";
            if (different.isSelected()) {
                if (!timeCheck.getText().isEmpty()) {
                    return;
                }
                hours = time.getText();
            } else {
                hours = "00:00-23:59";
            }

            String alertText = "";
            JSONObject obj = new JSONObject();


            LocalDate date = exceptionsDatePicker.getValue();
            String startDate = date.toString();
            String endDate = date.toString();
            String[] array = hours.split("-");
            startDate = startDate + "T" + array[0] + ":00";
            endDate = endDate + "T" + array[1] + ":00";
            String building = buildings.getSelectionModel().getSelectedItem();
            Long buildingNumber = ExceptionsPageContent.getBuilding(building);

            obj.put("buildingNumber", buildingNumber);
            obj.put("startingDate", startDate);
            obj.put("endDate", endDate);
            String message = ServerCommunication.sendBuildingException(obj);
            if (message.equals("OK")) {
                alertText = "The procedure was done correctly";
            } else {
                alertText = "The procedure was not  done correctly!" + "\n" + "Please try again!";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(alertText);
            alert.showAndWait();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
