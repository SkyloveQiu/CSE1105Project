package nl.tudelft.oopp.group43.views;

import static javafx.application.Application.launch;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ReservationPageContent;
import nl.tudelft.oopp.group43.components.BackButton;

public class ReservationDisplay {

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/reservationPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        /*
        Add the back button to the scene
         */
        BackButton btn = new BackButton();
        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        ap.getChildren().add(btn.getBackButton());
        BackButton.pushScene("reservation");

        startTimetableThread(primaryStage);

        primaryStage.setTitle("Campus Management - Reservation Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void startTimetableThread(Stage stage) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                ReservationPageContent rpc = new ReservationPageContent(stage);
                rpc.run();
            }
        });
        // Prevents JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

}
