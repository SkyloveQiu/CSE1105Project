package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.components.SideBarMenu;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import nl.tudelft.oopp.group43.content.ProfilePageContent;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;


public class ProfilePageController {

    @FXML
    private AnchorPane changePasswordMenu;

    @FXML
    private void showChangePasswordMenu(ActionEvent event) {
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
        ServerCommunication.setToken("invalid");
        SceneLoader.setScene("main");
        SceneLoader sl = new SceneLoader();
        try {
            sl.start((Stage) ((Node) event.getSource()).getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    private void navigateToMyReservations(MouseEvent e) {
        SceneLoader.setScene("myreservations");
        SceneLoader sl = new SceneLoader();
        try {
            sl.start((Stage) ((Node) e.getSource()).getScene().getWindow());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
