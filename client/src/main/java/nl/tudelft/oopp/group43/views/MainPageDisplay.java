package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

public class MainPageDisplay extends Application {

    private boolean clicked = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Label label = new Label();
        String buildings = ServerCommunication.getBuilding();
        System.out.println("All buildings" + buildings);
        label.setText("All Buildings: " + buildings);
        ScrollPane sp = (ScrollPane) scene.lookup("#buildings");
        sp.setContent(label);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
