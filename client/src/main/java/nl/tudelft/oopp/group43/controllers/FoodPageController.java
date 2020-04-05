package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.BikePageContent;
import nl.tudelft.oopp.group43.content.FoodPageContent;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

import java.io.IOException;
import java.util.ArrayList;

public class FoodPageController {
    public void orderFood(ActionEvent event) throws IOException {
        if (ServerCommunication.getToken().equals("invalid")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You are not logged in! Please login!");
            alert.showAndWait();

            SceneLoader.setScene("login");
            SceneLoader sl = new SceneLoader();
            sl.start((Stage) ((Node) event.getSource()).getScene().getWindow());
            return;
        }

        ArrayList<String> food = FoodPageContent.getSelectedFood();
        if (food.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You haven't selected any food!");
            alert.showAndWait();
            return;
        }
        String processedFood = String.join("-", food);



        String response = ServerCommunication.createFoodOrder(processedFood, "invalid");
        if (!response.equals("OK")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong!");
            alert.showAndWait();
            return;
        }
    }
}
