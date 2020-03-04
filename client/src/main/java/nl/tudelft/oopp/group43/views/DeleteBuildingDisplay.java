package nl.tudelft.oopp.group43.views;

import java.io.IOException;

import java.net.URL;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import nl.tudelft.oopp.group43.classes.BuildDeletePageContent;
import nl.tudelft.oopp.group43.classes.BuildingData;
import nl.tudelft.oopp.group43.classes.ThreadLock;



public class DeleteBuildingDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/DeleteBuildingScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        addBuildings(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management -  Delete Buildings");
        primaryStage.show();


    }

    /**
     * Adds all buildings as content using a new Thread.
     * Doing this removes initial startup lag.
     *
     * @param stage Stage is passed as parameter to get all Nodes
     */
    private void addBuildings(Stage stage) {


        ThreadLock  lock = new ThreadLock();

        BuildingData data = new BuildingData(lock);
        Thread threadData = new Thread(data);
        threadData.setDaemon(true);
        threadData.start();

        BuildDeletePageContent deletePage = new BuildDeletePageContent(stage, lock);
        Thread threadDeleteBuildingPage = new Thread(deletePage);
        threadDeleteBuildingPage.setDaemon(true);
        threadDeleteBuildingPage.start();

    }
}
