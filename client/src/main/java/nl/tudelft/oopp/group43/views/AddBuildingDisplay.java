package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.BackButton;


public class AddBuildingDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/AddBuildingScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        /*
        Add the back button to the scene
         */
//        BackButton btn = new BackButton();
//        AnchorPane ap = (AnchorPane) scene.lookup("#root");
//        ap.getChildren().add(btn.getBackButton());
//        BackButton.pushScene("add");
//
//        if (primaryStage.getScene() != null) {
//            ap.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
//        }

        primaryStage.setScene(scene);

        makeHours(primaryStage, "monday");
        makeHours(primaryStage, "tuesday");
        makeHours(primaryStage, "wednesday");
        makeHours(primaryStage, "thursday");
        makeHours(primaryStage, "friday");
        makeHours(primaryStage, "saturday");
        makeHours(primaryStage, "sunday");


        primaryStage.setTitle("Campus Management -  Add Buildings");
        primaryStage.show();
    }


    /**
     * Checks if the input data is OK - this function will be used as a listener for each group of 4 ChoiceBoxes.
     *
     * @param openH  - String represents the open hour
     * @param openM  - String represents the open minute
     * @param closeH - String represents close hour
     * @param closeM - String represents close minute
     * @param msg    - the label which will give the user proper messages about the input data
     */
    private void listenerFunction(String openH, String openM, String closeH, String closeM, Label msg) {
        boolean ok = false;
        if (ok == false && openH.equals("--") == true && (openH.equals(closeH) == false || openH.equals(closeM) == false || openH.equals(openM) == false)) {
            msg.setText("You have to put -- in all fields");
            ok = true;

        }
        if (ok == false && openM.equals("--") == true && (openM.equals(closeH) == false || openM.equals(closeM) == false || openM.equals(openH) == false)) {
            msg.setText("You have to put -- in all fields");
            ok = true;
        }
        if (ok == false && closeM.equals("--") == true && (closeM.equals(closeH) == false || closeM.equals(openM) == false || closeM.equals(openH) == false)) {
            msg.setText("You have to put -- in all fields");
            ok = true;
        }
        if (ok == false && closeH.equals("--") == true && (closeH.equals(openM) == false || closeH.equals(closeM) == false || closeH.equals(openH) == false)) {
            msg.setText("You have to put -- in all fields");
            ok = true;
        }
        if (ok == false) {
            String openString = openH + openM;
            String closeSring = closeH + closeM;
            if (openString.compareTo(closeSring) > 0) {
                msg.setText("The close hour must be after the open hour!!");
                ok = true;
            }
        }
        if (ok == false) {
            msg.setText("");
        }
    }

    /**
     * Puts the listeners to the ChoiceBoxes and calls functions to put data into choices fields.
     *
     * @param openH  - String represents open hour
     * @param openM  - String represents open minute
     * @param closeH - String represents close hour
     * @param closeM - String represents close minute
     * @param msg    - the label which will give the user proper messages about the input data
     */
    private void makeDay(ChoiceBox<String> openH, ChoiceBox<String> openM, ChoiceBox<String> closeH, ChoiceBox<String> closeM, Label msg) {
        openH.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != null) {
                    listenerFunction(newValue, openM.getSelectionModel().getSelectedItem(), closeH.getSelectionModel().getSelectedItem(), closeM.getSelectionModel().getSelectedItem(), msg);
                }
            }
        });
        openM.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != null) {
                    listenerFunction(openH.getSelectionModel().getSelectedItem(), newValue, closeH.getSelectionModel().getSelectedItem(), closeM.getSelectionModel().getSelectedItem(), msg);
                }
            }
        });
        closeH.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != null) {
                    listenerFunction(openH.getSelectionModel().getSelectedItem(), openM.getSelectionModel().getSelectedItem(), newValue, closeM.getSelectionModel().getSelectedItem(), msg);
                }
            }
        });
        closeM.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != null) {
                    listenerFunction(openH.getSelectionModel().getSelectedItem(), openM.getSelectionModel().getSelectedItem(), closeH.getSelectionModel().getSelectedItem(), newValue, msg);
                }
            }
        });
        putFileds(openH, openM);
        putFileds(closeH, closeM);
    }

    /**
     * Puts data into choices fields.
     *
     * @param hours   - ChoiceBox for hours
     * @param minutes - ChoiceBox for minutes
     */
    private void putFileds(ChoiceBox<String> hours, ChoiceBox<String> minutes) {
        hours.setValue("--");
        minutes.setValue("--");
        hours.getItems().add("--");
        minutes.getItems().add("--");
        for (int i = 0; i <= 59; i++) {
            String s = Integer.toString(i);
            if (s.length() == 1) {
                s = "0" + s;
            }
            if (i <= 23) {
                hours.getItems().add(s);
            }
            minutes.getItems().add(s);

        }

    }

    /**
     * Gets the ChoiceBoxes for each day and start to make listeners and put data into choices fields.
     *
     * @param stage - the actual stage
     * @param day   - String represents the day
     */
    private void makeHours(Stage stage, String day) {

        ChoiceBox<String> dayOpenH = (ChoiceBox) stage.getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> dayCloseH = (ChoiceBox) stage.getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> dayOpenM = (ChoiceBox) stage.getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> dayCloseM = (ChoiceBox) stage.getScene().lookup("#" + day + "CloseM");
        Label dayMsg = (Label) stage.getScene().lookup("#" + day + "Msg");
        makeDay(dayOpenH, dayOpenM, dayCloseH, dayCloseM, dayMsg);
    }
}
