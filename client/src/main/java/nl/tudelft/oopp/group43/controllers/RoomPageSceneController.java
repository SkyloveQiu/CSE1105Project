package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RoomPageSceneController {

    @SuppressWarnings("unchecked")
    public void backToMainPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
