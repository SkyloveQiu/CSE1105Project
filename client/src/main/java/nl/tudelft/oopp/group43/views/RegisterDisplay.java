package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.BackButton;

public class RegisterDisplay extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/RegisterScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        /*
        Add the back button to the scene
         */
        BackButton btn = new BackButton();
        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        ap.getChildren().add(btn.getBackButton());
        BackButton.pushScene("register");

        ToggleGroup radioButtons = new ToggleGroup();
        RadioButton studentbtn = (RadioButton) scene.lookup("#student");
        RadioButton employeebtn = (RadioButton) scene.lookup("#employee");
        RadioButton otherbtn = (RadioButton) scene.lookup("#other");
        studentbtn.setToggleGroup(radioButtons);
        employeebtn.setToggleGroup(radioButtons);
        otherbtn.setToggleGroup(radioButtons);

        if (primaryStage.getScene() != null) {
            ap.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
