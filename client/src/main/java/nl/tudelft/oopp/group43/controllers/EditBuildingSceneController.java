package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONObject;


public class EditBuildingSceneController {

    @FXML
    private Label editMsg;
    @FXML
    private Label editBuildingNumber;
    @FXML
    private TextField editBuildingName;
    @FXML
    private TextField editBuildingAddress;
    @FXML
    private Label editMondayMsg;
    @FXML
    private Label editTuesdayMsg;
    @FXML
    private Label editWednesdayMsg;
    @FXML
    private Label editThursdayMsg;
    @FXML
    private Label editFridayMsg;
    @FXML
    private Label editSaturdayMsg;
    @FXML
    private Label editSundayMsg;
    @FXML
    private Label editAddressCheck;
    @FXML
    private Label editNameCheck;

    /**
     * If you press the edit building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     * @throws IOException -  if loading the Main Page Display fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) throws IOException {

        if (editMsg.getText().isEmpty()) {
            editMsg.setText("You have not selected a building!");
            return;
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (editMondayMsg.getText().isEmpty() && editTuesdayMsg.getText().isEmpty() && editWednesdayMsg.getText().isEmpty() && editThursdayMsg.getText().isEmpty()
                && editFridayMsg.getText().isEmpty() && editSaturdayMsg.getText().isEmpty() && editSundayMsg.getText().isEmpty() && editNameCheck.getText().isEmpty() && editAddressCheck.getText().isEmpty()) {

            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", getHoursDay(stage, "monday"));
            openingHours.put("tu", getHoursDay(stage, "tuesday"));
            openingHours.put("we", getHoursDay(stage, "wednesday"));
            openingHours.put("th", getHoursDay(stage, "thursday"));
            openingHours.put("fr", getHoursDay(stage, "friday"));
            openingHours.put("sa", getHoursDay(stage, "saturday"));
            openingHours.put("su", getHoursDay(stage, "sunday"));

            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(editBuildingNumber.getText()));
            building.put("building_name", editBuildingName.getText());
            building.put("address", editBuildingAddress.getText());
            building.put("opening_hours", openingHours.toString());

            String a = ServerCommunication.sendEditBuilding(building);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (a.equals("OK")) {
                alert.setContentText("The building was successfully edited.");
            } else {
                alert.setContentText("NOT OK");
            }
            alert.showAndWait();

            /*
            MainPageDisplay md = new MainPageDisplay();
            md.start((Stage) ((Node) event.getSource()).getScene().getWindow());
             */
        }

    }


    /**
     * Gets the information regarding the opening hours of a day for a building.
     *
     * @param stage - the actual stage
     * @param day   - string with the day of the week
     * @return - the information regarding the opening hours of that day
     */
    @SuppressWarnings("unchecked")
    private String getHoursDay(Stage stage, String day) {
        ChoiceBox<String> openH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> openM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> closeH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> closeM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseM");

        String hours = openH.getSelectionModel().getSelectedItem() + ":" + openM.getSelectionModel().getSelectedItem();
        hours = hours + "-" + closeH.getSelectionModel().getSelectedItem() + ":" + closeM.getSelectionModel().getSelectedItem();

        if (hours.contains("--") == true) {
            hours = "closed";
        }
        return hours;
    }

    /**
     * Checks if the Building Name field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkName() {
        if (editBuildingName.getText().isEmpty()) {
            editNameCheck.setText("You cannot have this field empty");
        } else {
            editNameCheck.setText("");
        }
    }

    /**
     * Checks if the Address field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkAddress() {
        if (editBuildingAddress.getText().isEmpty()) {
            editAddressCheck.setText("You cannot have this field empty");
        } else {
            editAddressCheck.setText("");
        }
    }


}
