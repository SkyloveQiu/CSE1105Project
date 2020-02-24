package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

import java.io.IOException;
import java.net.URL;

public class MainPageController {

    private boolean clicked = false;

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

//        Scene scene = (Scene) ((Node) event.getSource()).getScene();

        if(clicked) {
            menubar.relocate(0.0, 0.0);
            menubutton.setText("Close");
        }
        else {
            menubar.relocate(-180.0, 0.0);
            menubutton.setText("Menu");
        }
    }

    @SuppressWarnings("unchecked")
    public void toRoomPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/roomPageScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void toLoginPage(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/loginScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @SuppressWarnings("unchecked")
    public void showUsers(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An user for you");
        alert.setHeaderText(null);

        alert.setContentText(ServerCommunication.getUsers());

        alert.showAndWait();
    }

    @SuppressWarnings("unchecked")
    public void showRooms(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An user for you");
        alert.setHeaderText(null);

        alert.setContentText(ServerCommunication.getRooms());

        alert.showAndWait();
    }

    @SuppressWarnings("unchecked")
    public void showBuildings(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An user for you");
        alert.setHeaderText(null);

        alert.setContentText(ServerCommunication.getBuilding());

        alert.showAndWait();
    }
}