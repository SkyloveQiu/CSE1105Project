package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONObject;


public class EditBuildingSceneController {

    @FXML
    Label editMsg;
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

    /**
     * If you press the delete building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     * @throws IOException -  if loading the Main Page Display fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        if (mondayMsg.getText().isEmpty() && tuesdayMsg.getText().isEmpty() && wednesdayMsg.getText().isEmpty() && thursdayMsg.getText().isEmpty()
                && fridayMsg.getText().isEmpty() && saturdayMsg.getText().isEmpty() && sundayMsg.getText().isEmpty()) {
            System.out.println(buildingNumber.getText());
            System.out.println(buildingName.getText());
            System.out.println(buildingAddress.getText());
            System.out.println(getHoursDay(stage, "monday"));
            System.out.println(getHoursDay(stage, "tuesday"));
            System.out.println(getHoursDay(stage, "wednesday"));
            System.out.println(getHoursDay(stage, "thursday"));
            System.out.println(getHoursDay(stage, "friday"));
            System.out.println(getHoursDay(stage, "saturday"));
            System.out.println(getHoursDay(stage, "sunday"));
            //  System.out.println(getHoursDay(stage, "monday"));

            JSONObject opening_hours = new JSONObject();
            opening_hours.put("mo", getHoursDay(stage, "monday"));
            opening_hours.put("tu", getHoursDay(stage, "tuesday"));
            opening_hours.put("we", getHoursDay(stage, "wednesday"));
            opening_hours.put("th", getHoursDay(stage, "thursday"));
            opening_hours.put("fr", getHoursDay(stage, "friday"));
            opening_hours.put("sa", getHoursDay(stage, "saturday"));
            opening_hours.put("su", getHoursDay(stage, "sunday"));

            JSONObject building = new JSONObject();
            building.put("building_number", buildingNumber.getText());
            building.put("building_name", buildingName.getText());
            building.put("address", buildingAddress.getText());
            building.put("opening_hours", opening_hours.toString());
            String s =  ServerCommunication.sendEditBuilding(building);
            System.out.println(building);
            System.out.println(s);

        }

       /* if (editMsg.getText() == null) {
            editMsg.setText("You have not selected a building!");
            return;
        }
        if (numberCheck.getText() != null) {
            editMsg.setText("Something goes wrong with your input!");
            return;
        }*/


        //String a = ServerCommunication.sendDeleteBuilding(getBuildingID());
        // editMsg.setText("");
        //   Alert alert = new Alert(Alert.AlertType.INFORMATION);
     /*   if (a.equals("OK")) {
            alert.setContentText("The building was successfully deleted.");
        } else {
            alert.setContentText("NOT OK");
        }
        alert.showAndWait();*/

        //   MainPageDisplay md = new MainPageDisplay();
        //  md.start((Stage) ((Node) event.getSource()).getScene().getWindow());

    }

    /**
     * Checks if the user put a number which is grater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkNumber() {
        try {
            String nunmberString = buildingNumber.getText();
            System.out.println(Long.MAX_VALUE);
            long number = Long.valueOf(nunmberString);
            numberCheck.setText("");
        } catch (Exception e) {
            numberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
        }
    }

    /**
     * Gets the information regarding the opening hours of a day for a building.
     *
     * @param stage - the actual stage
     * @param day   - string with the day of the week
     * @return - the information regarding the opening hours of that day
     */
    private String getHoursDay(Stage stage, String day) {
        ChoiceBox<String> openH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> openM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> closeH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> closeM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseM");

        String hours = openH.getSelectionModel().getSelectedItem() + ":" + openM.getSelectionModel().getSelectedItem();
        hours = hours + "-" + closeH.getSelectionModel().getSelectedItem() + ":" + closeM.getSelectionModel().getSelectedItem();

        if (hours.contains("--") == true){
            hours = "closed";
        }
        return hours;
    }


}
