package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

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
}
