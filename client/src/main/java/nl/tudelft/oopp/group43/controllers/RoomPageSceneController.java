package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.views.MainPageDisplay;

import java.io.IOException;
import java.net.URL;

public class RoomPageSceneController {

    private boolean clicked = false;

    @FXML
    private Button menubutton;

    @FXML
    private Pane menubar;

    @FXML
    @SuppressWarnings("unchecked")
    private void toMainPage(ActionEvent e) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        URL xmlUrl = getClass().getResource("/mainPage.fxml");
//        loader.setLocation(xmlUrl);
//        Parent root = loader.load();
//
//        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();

        MainPageDisplay mp = new MainPageDisplay();
        mp.start((Stage) ((Node) e.getSource()).getScene().getWindow());
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void toLoginPage(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/LoginScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Campus Management - Login Page");
        stage.show();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void menuClicked(ActionEvent e) {
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
}
