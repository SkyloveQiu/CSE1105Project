package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
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

        ScrollPane sp = (ScrollPane) scene.lookup("#buildings");

        Button btn1 = new Button();
        btn1.setLayoutX(50.0);
        btn1.setLayoutY(40.0);
        btn1.setText("buildings");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("An building for you");
                alert.setHeaderText(null);

                alert.setContentText(ServerCommunication.getBuilding());

                alert.showAndWait();
            }
        });

        Button btn2 = new Button();
        btn2.setLayoutX(50.0);
        btn2.setLayoutY(80.0);
        btn2.setText("rooms");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("An room for you");
                alert.setHeaderText(null);

                alert.setContentText(ServerCommunication.getRooms());

                alert.showAndWait();
            }
        });

        Button btn3 = new Button();
        btn3.setLayoutX(50.0);
        btn3.setLayoutY(120.0);
        btn3.setText("users");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("An user for you");
                alert.setHeaderText(null);

                alert.setContentText(ServerCommunication.getUsers());

                alert.showAndWait();
            }
        });

        Pane pane = new Pane(btn1, btn2, btn3);
        sp.setContent(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
