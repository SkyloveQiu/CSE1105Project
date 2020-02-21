package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainPageDisplay extends Application {

    private boolean clicked = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        ScrollPane buildings = new ScrollPane();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
