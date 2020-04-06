package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.BikePageContent;
import nl.tudelft.oopp.group43.content.FoodPageContent;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;


public class FoodPageController {
    /**
     * Order food.
     * @param event event
     * @throws IOException exception
     */
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

        if (FoodPageContent.getSelectedTime() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You haven't selected any time!");
            alert.showAndWait();
            return;
        }

        String body = "{\"building\":\"" + FoodPageContent.getSelectedBuilding() + "\",\"reservation\":\"" + FoodPageContent.getSelectedReservation() + "\",\"user\":\"" + ServerCommunication.getUsername() + "\",\"time\":\"" + FoodPageContent.getSelectedTime() + "\"}";

        String response = ServerCommunication.createFoodOrder(processedFood, FoodPageContent.getTakeaway(), body);
        if (!response.equals("OK")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something went wrong!");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You reserved your food!");
            alert.showAndWait();
            return;
        }
    }
}
