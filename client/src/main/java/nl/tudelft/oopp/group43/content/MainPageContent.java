package nl.tudelft.oopp.group43.content;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

import java.io.IOException;

public class MainPageContent {

    private static Scene scene;

    public static void addContent(Scene currentScene) {
        scene = currentScene;

        Label log = (Label) scene.lookup("#logged_in");

        if (!ServerCommunication.getToken().equals("invalid")) {
            log.setText(ServerCommunication.getUsername());
        } else {
            log.setText("Sign-In");
            log.setStyle("-fx-background-color: seagreen; -fx-background-radius: 15 15 15 15; -fx-border-width: 1 1 1 1; -fx-border-color: black; -fx-border-radius: 15 15 15 15;");

            log.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    log.setStyle("-fx-background-color: palegreen; -fx-background-radius: 15 15 15 15; -fx-border-width: 1 1 1 1; -fx-border-color: silver; -fx-border-radius: 15 15 15 15;");
                }
            });

            log.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    log.setStyle("-fx-background-color: seagreen; -fx-background-radius: 15 15 15 15; -fx-border-width: 1 1 1 1; -fx-border-color: black; -fx-border-radius: 15 15 15 15;");
                }
            });

            log.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    SceneLoader.setScene("login");
                    SceneLoader sl = new SceneLoader();
                    try {
                        sl.start((Stage) scene.getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
