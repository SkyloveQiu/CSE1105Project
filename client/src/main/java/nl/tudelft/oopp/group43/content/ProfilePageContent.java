package nl.tudelft.oopp.group43.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ProfilePageContent {

    private static double windowHeight = 800;

    public static void addContent(Scene scene) {
        Button changePasswordButton = (Button) scene.lookup("#changePwd");
        GridPane.setHalignment(changePasswordButton, HPos.CENTER);

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

