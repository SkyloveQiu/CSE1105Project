package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;


public class DeleteBuildingSceneController {

    @FXML
    private Label deleteMsg;


    /**
     * If you press the delete building button, the method will check if you selected a building and do the delete operation.
     * @param event - pressing the button
     * @throws IOException -  if loading the Main Page Display fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void deleteConfirm(ActionEvent event) throws IOException {

        if (deleteMsg.getText() == null) {
            deleteMsg.setText("You have not selected a building!");
            return;
        }


        String a = ServerCommunication.sendDeleteBuilding(getBuildingID());
        deleteMsg.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (a.equals("OK")) {
            alert.setContentText("The building was successfully deleted.");
        } else {
            alert.setContentText("NOT OK");
        }
        alert.showAndWait();
    }

    /**
     * Analyse the delete message, it extracts the id of the building.
     * @return - the id of the building which is selected.
     */
    private String getBuildingID() {
        String sentence = deleteMsg.getText();
        String[] words = sentence.split("\\s+");
        String buildingId = words[9];
        buildingId = buildingId.substring(0, buildingId.length() - 1);

        return buildingId;
    }

}
