package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.views.DeleteBuildingDisplay;
import nl.tudelft.oopp.group43.views.RegisterDisplay;

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

        if (clicked) {
            menubar.relocate(0.0, 0.0);
            menubutton.setText("Close");
        } else {
            menubar.relocate(-180.0, 0.0);
            menubutton.setText("Menu");
        }
    }

    /**
     * Goes to the room page when clicked on.
     * @param event event passed by button when clicked on.
     * @throws IOException throws the exception when loading the FXML file goes wrong.
     */
    @SuppressWarnings("unchecked")
    public void toRoomPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/roomPageScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Campus Management - Room Menu");
        stage.show();
    }

    /**
     * If you press the confirm button, you will be redirected to the login page if all fields are valid.
     * @param event - pressing the button
     * @throws IOException - if loading the Login Scene fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toLoginPage(ActionEvent event) throws IOException{
        LoginDisplay ld = new LoginDisplay();
        ld.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }


    /**
     * If you press the delete building button, you will be redirected to the delete building scene.
     * @param event - pressing the button
     * @throws IOException - if loading the Delete Building Scene fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toDeleteBuilding(ActionEvent event) throws IOException {

        DeleteBuildingDisplay rd = new DeleteBuildingDisplay();
        rd.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

}
