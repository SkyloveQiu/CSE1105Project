package nl.tudelft.oopp.group43.views;

import static javafx.application.Application.launch;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.ReservationPageContent;
import nl.tudelft.oopp.group43.components.BackButton;

public class ReservationDisplay {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the reservation page.
     *
     * @param primaryStage the stage of which the scene needs to be changed
     * @throws IOException throws an exception when there is a problem loading the scene
     */
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/reservationPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        /*
        Add the back button to the scene
         */
//        BackButton btn = new BackButton();
//        AnchorPane ap = (AnchorPane) scene.lookup("#root");
//        ap.getChildren().add(btn.getBackButton());
        /*
        pushes the selected room and building to the scene stack for later use in the format:
        buildingID;roomID
         */
        BackButton.pushScene(ReservationConfig.getSelectedBuilding() + ";" + ReservationConfig.getSelectedRoom());

        startTimetableThread(primaryStage);

//        if (primaryStage.getScene() != null) {
//            ap.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
//        }

        primaryStage.setTitle("Campus Management - Reservation Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
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
