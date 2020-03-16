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

public class AddBuildingSceneController {

    @FXML
    TextField buildingNumber;
    @FXML
    Label numberCheck;
    @FXML
    TextField buildingName;
    @FXML
    TextField buildingAddress;
    @FXML
    Label mondayMsg;
    @FXML
    Label tuesdayMsg;
    @FXML
    Label wednesdayMsg;
    @FXML
    Label thursdayMsg;
    @FXML
    Label fridayMsg;
    @FXML
    Label saturdayMsg;
    @FXML
    Label sundayMsg;
    @FXML
    Label addressCheck;
    @FXML
    Label nameCheck;

    /**
     * If you press the add building button, the method will try to do this operation, it it will be possible.
     *
     * @param event - pressing the button
     * @throws IOException -  if loading the Main Page Display fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void addConfirm(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean ok = checkNumber();
        ok = checkAddress() && ok;
        ok = checkName() && ok;
        if (mondayMsg.getText().isEmpty() && tuesdayMsg.getText().isEmpty() && wednesdayMsg.getText().isEmpty() && thursdayMsg.getText().isEmpty() && fridayMsg.getText().isEmpty()
                && saturdayMsg.getText().isEmpty() && sundayMsg.getText().isEmpty() && ok) {

            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", getHoursDay(stage, "monday"));
            openingHours.put("tu", getHoursDay(stage, "tuesday"));
            openingHours.put("we", getHoursDay(stage, "wednesday"));
            openingHours.put("th", getHoursDay(stage, "thursday"));
            openingHours.put("fr", getHoursDay(stage, "friday"));
            openingHours.put("sa", getHoursDay(stage, "saturday"));
            openingHours.put("su", getHoursDay(stage, "sunday"));

            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(buildingNumber.getText()));
            building.put("building_name", buildingName.getText());
            building.put("address", buildingAddress.getText());
            building.put("opening_hours", openingHours.toString());
            String a = ServerCommunication.sendAddBuilding(building);

            boolean alertOK = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (a.equals("OK")) {
                alert.setContentText("The building was successfully added.");
                alertOK = true;
            } else {
                alert.setContentText("A building with this number already exists!!! \nPlease change the building number!");
            }
            alert.showAndWait();

            if (alertOK) {
//                MainPageDisplay md = new MainPageDisplay();
//                md.start((Stage) ((Node) event.getSource()).getScene().getWindow());
            }


        }

    }

    /**
     * Checks if the user put a number which is grater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkNumber() {
        if (buildingNumber.getText().isEmpty()) {
            numberCheck.setText("You cannot have this field empty");
            return false;
        }
        try {
            String nunmberString = buildingNumber.getText();
            System.out.println(Long.MAX_VALUE);
            long number = Long.valueOf(nunmberString);
            numberCheck.setText("");
            return true;
        } catch (Exception e) {
            numberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
            return false;
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
    private boolean checkName() {
        if (buildingName.getText().isEmpty()) {
            nameCheck.setText("You cannot have this field empty");
            return false;
        } else {
            nameCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the Address field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkAddress() {
        if (buildingAddress.getText().isEmpty()) {
            addressCheck.setText("You cannot have this field empty");
            return false;
        } else {
            addressCheck.setText("");
            return true;
        }
    }


}
