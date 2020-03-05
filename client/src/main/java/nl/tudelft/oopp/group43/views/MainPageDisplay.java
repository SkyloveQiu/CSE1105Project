package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.BuildingsConfig;
import nl.tudelft.oopp.group43.classes.MainPageContent;

public class MainPageDisplay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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
                if ((double) newValue >= 700.0 && gp.getColumnCount() < 3) {
                    for (ColumnConstraints cc : gp.getColumnConstraints()) {
                        cc.setPercentWidth(33.33);
                    }
                    ColumnConstraints cc = new ColumnConstraints();
                    cc.setPercentWidth(33.33);
                    gp.getColumnConstraints().add(cc);
                    BuildingsConfig.setColumnCount(3);

                    updateGrid(gp);
                }

                if ((double) newValue < 700.0 && gp.getColumnCount() > 2) {
                    gp.getColumnConstraints().remove(2);
                    for (ColumnConstraints cc : gp.getColumnConstraints()) {
                        cc.setPercentWidth(50.0);
                    }
                    BuildingsConfig.setColumnCount(2);

                    updateGrid(gp);
                }

                for (RowConstraints rc : gp.getRowConstraints()) {
                    double newSize = ((double) newValue - 60.0) / BuildingsConfig.getColumnCount();
                    rc.setPrefHeight(newSize);
                    rc.setMinHeight(newSize);
                    rc.setMaxHeight(newSize);
                }
            }
        };
        sp.widthProperty().addListener(resizeListener);

        addBuildings(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();

        // Sets label for letting user know it is being loaded
        Label label = (Label) scene.lookup("#titlebar");
        label.setText(label.getText() + " - BUILDINGS ARE BEING LOADED");
    }

    /**
     * Adds all buildings as content using a new Thread.
     * Doing this removes initial startup lag.
     *
     * @param stage Stage is passed as parameter to get all Nodes
     */
    private void addBuildings(Stage stage) {
        //Adds the building content in a separate Thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable r = new MainPageContent(stage);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(r);
            }
        });
        // Prevents JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Updates the grid when it gets resized.
     * Namely by adding columns when it hit a certain size point (700.0)
     * and adding or removing rows accordingly.
     *
     * @param gp the GridPane that gets updates
     */
    private void updateGrid(GridPane gp) {
        gp.getChildren().removeAll(BuildingsConfig.getLabel());

        int rows = BuildingsConfig.getLabel().length / BuildingsConfig.getColumnCount();
        if (BuildingsConfig.getLabel().length % BuildingsConfig.getColumnCount() != 0) {
            rows++;
        }
        if (rows < gp.getRowCount()) {
            for (int i = gp.getRowCount() - 1; i >= rows; i--) {
                gp.getRowConstraints().remove(i);
            }
        }
        while (rows > gp.getRowCount()) {
            RowConstraints rc = new RowConstraints();
            double newSize = (gp.getWidth() - 60.0) / BuildingsConfig.getColumnCount();
            rc.setPrefHeight(newSize);
            rc.setMinHeight(newSize);
            rc.setMaxHeight(newSize);
            gp.getRowConstraints().add(rc);
        }

        int row = 0;
        for (int i = 0; i < BuildingsConfig.getLabel().length; i++) {
            if (i % BuildingsConfig.getColumnCount() == 0 && i != 0) {
                row++;
            }
            gp.add(BuildingsConfig.getLabel()[i], i % BuildingsConfig.getColumnCount(), row);
        }
    }

}
