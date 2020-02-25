package nl.tudelft.oopp.group43.views;

import javafx.application.Platform;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.MainPageContent;

public class MainPageDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        ScrollPane sp = (ScrollPane) scene.lookup("#buildings");
        GridPane gp = (GridPane) scene.lookup("#buildings_grid");

        ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane gp = (GridPane) scene.lookup("#buildings_grid");
                for(RowConstraints rc : gp.getRowConstraints()) {
                    double newSize = ((double) newValue - 60.0)/2;
                    rc.setPrefHeight(newSize);
                    rc.setMinHeight(newSize);
                    rc.setMaxHeight(newSize);
                }
            }
        };
        sp.widthProperty().addListener(resizeListener);

        //Adds the building content in a seperate Thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable r = new MainPageContent(primaryStage);

                try { Thread.sleep(500); }
                catch(InterruptedException e) {}

                Platform.runLater(r);
            }
        });
        // Prevents JVM shutdown
        thread.setDaemon(true);
        thread.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();

        // Sets label for letting user know it is being loaded
        Label label = (Label) scene.lookup("#titlebar");
        label.setText(label.getText() + " - BUILDINGS ARE BEING LOADED");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
