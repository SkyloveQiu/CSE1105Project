package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.BackButton;


public class FilterDisplay extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/FilteringScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        
        /*
        Add the back button to the scene
         */
        BackButton btn = new BackButton(new ImageView());
        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        ap.getChildren().add(btn.getBackButton());
        BackButton.pushScene("filterS");

        if (primaryStage.getScene() != null) {
            ap.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
        }

        primaryStage.setTitle("Campus Management - Filter rooms");
        primaryStage.show();



    }
}
