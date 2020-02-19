package nl.tudelft.oopp.demo.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
//import javafx.scene.transform.Translate;

public class MainPageController {

    private boolean clicked = false;

    /**
     * Handles clicking the button.
     */
    @SuppressWarnings("unchecked")
    public void buttonClicked(ActionEvent event) {
        this.clicked = !this.clicked;

        Scene scene = (Scene) ((Node) event.getSource()).getScene();
        Pane menuBar = (Pane) scene.lookup("#menubar");
        Button btn = (Button) scene.lookup("#menubutton");

        if(this.clicked) {
            menuBar.relocate(0.0, 0.0);
            btn.setText("Close");
        }
        else {
            menuBar.relocate(-170.0, 0.0);
            btn.setText("Menu");
        }
    }
}
