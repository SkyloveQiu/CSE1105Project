package nl.tudelft.oopp.group43.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

import java.io.IOException;

public class MainPageController {

    @FXML
    private void navigateToRooms(MouseEvent e) throws IOException {
        SceneLoader.setScene("room");
        SceneLoader sl = new SceneLoader();
        sl.start((Stage) ((Node) e.getSource()).getScene().getWindow());
    }

}
