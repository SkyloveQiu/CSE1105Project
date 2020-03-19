package nl.tudelft.oopp.group43.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.content.BikePageContent;

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

}
