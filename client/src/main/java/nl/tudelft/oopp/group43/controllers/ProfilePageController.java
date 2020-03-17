package nl.tudelft.oopp.group43.controllers;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.content.ProfilePageContent;

public class ProfilePageController {

    @FXML
    private AnchorPane changePasswordMenu;

    @FXML
    private void showMyReservationsMenu(ActionEvent event) {
    }

    @FXML
    private void showChangePasswordMenu(ActionEvent event){
        TranslateTransition st = new TranslateTransition(Duration.seconds(1), changePasswordMenu);
        st.setFromY(ProfilePageContent.getWindowHeight() + 40.0);
        st.setToY(141.0);
        st.setCycleCount(1);

        st.play();
        changePasswordMenu.setVisible(true);
    }
    @FXML
    private void logOut(ActionEvent event) {

    }
    @FXML
    private void closeChangePasswordMenu(ActionEvent actionEvent) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), changePasswordMenu);
        trans.setFromY(141.0);
        trans.setToY(ProfilePageContent.getWindowHeight() + 40);
        trans.setCycleCount(1);

        trans.setOnFinished(e -> changePasswordMenu.setVisible(false));
        trans.play();
    }
}
