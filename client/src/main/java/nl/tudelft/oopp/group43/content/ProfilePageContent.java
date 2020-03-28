package nl.tudelft.oopp.group43.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

public class ProfilePageContent {

    private static double windowHeight = 800;

    /** Adds name to Profile Page and makes the buttons scalable.
     *
     * @param scene - the scene currently in use, which gets manipulated.
     */
    public static void addContent(Scene scene) {
        Label log = (Label) scene.lookup("#userAccountName");
        log.setText(ServerCommunication.getFirstName() + "'s account");
        log.setStyle("-fx-text-fill: aliceblue;");


        Button changePasswordButton = (Button) scene.lookup("#changePwd");
        GridPane.setHalignment(changePasswordButton, HPos.CENTER);

        Button myReservationsButton = (Button) scene.lookup("#myreservations");
        GridPane.setHalignment(myReservationsButton, HPos.CENTER);

        Button logOutButton = (Button) scene.lookup("#logout");
        GridPane.setHalignment(logOutButton, HPos.CENTER);

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setWindowHeight((double) newValue);
            }
        });
    }

    public static void setWindowHeight(double height) {
        windowHeight = height;
    }

    public static double getWindowHeight() {
        return windowHeight;
    }
}

