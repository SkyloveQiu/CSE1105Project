package nl.tudelft.oopp.demo.controllers;

import javafx.scene.control.Alert;
import nl.tudelft.oopp.demo.communication.ServerCommunication;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

public class MainSceneController {

    /**
     * Handles clicking the button.
     */
    @SuppressWarnings("unchecked")
    public void buttonClicked() {
        JSONParser parser = new JSONParser();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("An user for you");
        alert.setHeaderText(null);

        try {
            Object obj = parser.parse(ServerCommunication.getUser());
            JSONObject user = (JSONObject) obj;
            alert.setContentText("User: " + user.get("firstName") + "\nLastname: " + user.get("lastName"));
        }
        catch(ParseException e) {}


        alert.showAndWait();
    }
}
