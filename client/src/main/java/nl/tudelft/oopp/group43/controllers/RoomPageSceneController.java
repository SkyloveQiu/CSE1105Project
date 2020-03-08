package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.views.LoginDisplay;
import nl.tudelft.oopp.group43.views.MainPageDisplay;

public class RoomPageSceneController {

    private boolean clicked = false;

    @FXML
    private Button menubutton;

    @FXML
    private Pane menubar;

    /**
     * Handles clicking the menu button.
     */
    @SuppressWarnings("unchecked")
    public void menuClicked(ActionEvent event) {
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
     * Returns to the main page with the button in the sidemenu.
     * @param e the event that gets passed by clicking on the button.
     * @throws IOException Throws an exception if it cannot load the page file.
     */
    @SuppressWarnings("unchecked")
    public void toMainPage(ActionEvent e) throws IOException {
        MainPageDisplay mp = new MainPageDisplay();
        mp.start((Stage) ((Node) e.getSource()).getScene().getWindow());
        BackButton.pushScene("room");
    }

    /**
     * Moves you to the login page.
     * @param event the event passed by the button when clicked.
     * @throws IOException throws this exception when.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void toLoginPage(ActionEvent event) throws IOException {
        LoginDisplay ld = new LoginDisplay();
        ld.start((Stage) ((Node) event.getSource()).getScene().getWindow());
        BackButton.pushScene("room");
    }
}
