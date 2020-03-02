package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import nl.tudelft.oopp.group43.views.MainPageDisplay;

public class RoomPageSceneController {

    /**
     * Returns to the main page with the button in the sidemenu.
     * @param e the event that gets passed by clicking on the button.
     * @throws IOException Throws an exception if it cannot load the page file.
     */
    @SuppressWarnings("unchecked")
    public void backToMainPage(ActionEvent e) throws IOException {
        MainPageDisplay mp = new MainPageDisplay();
        mp.start((Stage) ((Node) e.getSource()).getScene().getWindow());
    }
}
