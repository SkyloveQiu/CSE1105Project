package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.BikePageContent;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class BikePageController {

    @FXML
    private AnchorPane rentMenu;
    @FXML
    private AnchorPane returnMenu;
    @FXML
    private AnchorPane reserveMenu;


    @FXML
    private void showReturnBikeMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), returnMenu);
        trans.setFromY(BikePageContent.getWindowHeight() + 40.0);
        trans.setToY(141.0);
        trans.setCycleCount(1);

        trans.play();
        returnMenu.setVisible(true);
    }

    @FXML
    private void showRentBikeMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), rentMenu);
        trans.setFromY(BikePageContent.getWindowHeight() + 40.0);
        trans.setToY(141.0);
        trans.setCycleCount(1);

        trans.play();
        rentMenu.setVisible(true);
    }

    @FXML
    private void showReserveBikeMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), reserveMenu);
        trans.setFromY(BikePageContent.getWindowHeight() + 40.0);
        trans.setToY(141.0);
        trans.setCycleCount(1);

        trans.play();
        reserveMenu.setVisible(true);
    }

    @FXML
    private void closeRentMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), rentMenu);
        trans.setFromY(141.0);
        trans.setToY(BikePageContent.getWindowHeight() + 40);
        trans.setCycleCount(1);

        trans.setOnFinished(e -> rentMenu.setVisible(false));
        trans.play();
    }

    @FXML
    private void closeReturnMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), returnMenu);
        trans.setFromY(141.0);
        trans.setToY(BikePageContent.getWindowHeight() + 40);
        trans.setCycleCount(1);

        trans.setOnFinished(e -> returnMenu.setVisible(false));
        trans.play();
    }

    @FXML
    private void closeReserveMenu(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), reserveMenu);
        trans.setFromY(141.0);
        trans.setToY(BikePageContent.getWindowHeight() + 40);
        trans.setCycleCount(1);

        trans.setOnFinished(e -> reserveMenu.setVisible(false));
        trans.play();
    }

    /**
     * /**
     * Checks if a building is selected and give to the user an available bike for that building (if it is possible).
     *
     * @param event - the button "Rent" is pressed.
     * @throws ParseException - if something goes wrong with the JSON parser.
     * @throws IOException    - if something oes wrong moving to the Login Page.
     */
    @FXML
    private void confirmRent(ActionEvent event) throws ParseException, IOException {
        String selectedBuilding = BikePageContent.getSelectedBuilding();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String alertInformation;
        if (ServerCommunication.getToken().equals("invalid")) {
            alertInformation = "You are not log in yet! Please login!";
            alert.setContentText(alertInformation);
            alert.showAndWait();
            SceneLoader.setScene("login");
            SceneLoader sl = new SceneLoader();
            sl.start((Stage) ((Node) event.getSource()).getScene().getWindow());
            return;
        }
        boolean ok = false;
        if (selectedBuilding == null) {
            alertInformation = "You have not selected any building!" + "\n" + " Please choose one!";
        } else {
            System.out.println(selectedBuilding);
            String bikes = ServerCommunication.getBikeRenting(selectedBuilding);
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(bikes);
            if (jsonArray.size() == 0) {
                alertInformation = "This building doesn't have any available bikes!";
            } else {
                JSONObject obj = (JSONObject) jsonArray.get(0);

                String bikeID = Long.toString((Long) obj.get("bikeId"));
                String message = ServerCommunication.sendBikeRenting(bikeID);
                if (!message.equals("OK")) {
                    alertInformation = "Check your connection with the server! Something goes wrong with it";
                } else {
                    alertInformation = "Your reservation was successful proceed!" + "\n" + "You reserved the bike with ID  " + obj.get("bikeId");
                    ok = true;
                }
            }

        }
        alert.setContentText(alertInformation);
        alert.showAndWait();
        if (ok) {
            closeRentMenu(event);
        }
    }

}
