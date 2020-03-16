package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

public class MainPageController {

    /**
     * Navigates to the rooms when a certain label gets clicked.
     * @param e Event passed by the box when clicked on
     */
    @FXML
    private void navigateToRooms(MouseEvent e) {
        SceneLoader.setScene("room");
        SceneLoader sl = new SceneLoader();
        try {
            sl.start((Stage) ((Node) e.getSource()).getScene().getWindow());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
