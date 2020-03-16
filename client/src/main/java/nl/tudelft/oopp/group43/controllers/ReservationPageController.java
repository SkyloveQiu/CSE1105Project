package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.ReservationPageContent;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.views.DeleteBuildingDisplay;
import nl.tudelft.oopp.group43.views.EditBuildingDisplay;

public class ReservationPageController {

    private boolean clicked = false;

    @FXML
    private Label progress;

    @FXML
    private Button menubutton;

    @FXML
    private Pane menubar;

    /**
     * Handles clicking the button.
     */
    @SuppressWarnings("unchecked")
    public void buttonClicked(ActionEvent event) {
        this.clicked = !this.clicked;

        if (clicked) {
            menubar.relocate(0.0, 0.0);
            menubutton.setText("Close");
        } else {
            menubar.relocate(-180.0, 0.0);
            menubutton.setText("Menu");
        }
    }

    /**
     * If you press the confirm button, you will be redirected to the login page if all fields are valid.
     * @param event - pressing the button
     * @throws IOException - if loading the Login Scene fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toLoginPage(ActionEvent event) throws IOException {
//        LoginDisplay ld = new LoginDisplay();
//        ld.start((Stage) ((Node) event.getSource()).getScene().getWindow());
//        BackButton.pushScene("reservation");
    }


    /**
     * If you press the delete building button, you will be redirected to the delete building scene.
     * @param event - pressing the button
     * @throws IOException - if loading the Delete Building Scene fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toDeleteBuilding(ActionEvent event) throws IOException {
        BackButton.pushScene("reservation");
        DeleteBuildingDisplay rd = new DeleteBuildingDisplay();
        rd.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * If you press the edit building button, you will be redirected to the edit building scene.
     * @param event - pressing the button
     * @throws IOException - if loading the Edit Building Scene fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toEditBuilding(ActionEvent event) throws IOException {
        BackButton.pushScene("reservation");
        EditBuildingDisplay ed = new EditBuildingDisplay();
        ed.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * When this button is pressed, a POST request is made for the selected hours.
     * @param e - event passed when pressing the button
     */
    @FXML
    private void confirmReservation(ActionEvent e) {
        ArrayList selectedHours = ReservationConfig.getSelectedHours();

        progress.setText("The room is being reserved for the selected timeslots,\nPlease wait...");

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
        }

        ReservationPageContent.setDateString("");
        progress.setText(response);
    }
}
