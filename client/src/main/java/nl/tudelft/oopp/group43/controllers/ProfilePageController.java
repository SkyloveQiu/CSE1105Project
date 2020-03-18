package nl.tudelft.oopp.group43.controllers;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
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
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), changePasswordMenu);
        st.setFromX(0);
        st.setToX(1);
        st.setFromY(0);
        st.setToY(1);
        st.setCycleCount(1);

        st.play();
        changePasswordMenu.setVisible(true);
    }
    @FXML
    private void logOut(ActionEvent event) {

    }
    @FXML
    private void closeChangePasswordMenu(ActionEvent actionEvent) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), changePasswordMenu);
        st.setFromX(1);
        st.setToX(0);
        st.setFromY(1);
        st.setToY(0);
        st.setCycleCount(1);

        st.setOnFinished(e -> changePasswordMenu.setVisible(false));
        st.play();
    }
}
