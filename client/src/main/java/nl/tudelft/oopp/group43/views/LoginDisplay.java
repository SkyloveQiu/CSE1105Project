package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.BackButton;

public class LoginDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/LoginScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        /*
        Add the back button to the scene
         */
//        BackButton btn = new BackButton();
//        AnchorPane ap = (AnchorPane) scene.lookup("#root");
//        ap.getChildren().add(btn.getBackButton());
//        BackButton.pushScene("login");

        ChangeListener<Number> widthResizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ImageView image = (ImageView) scene.lookup("#image");
                AnchorPane ap = (AnchorPane) scene.lookup("#anchor");

                double apWidthOffset = image.getFitHeight() * 0.7076566;
                double apWidth = scene.getWidth() - apWidthOffset;

                ap.setPrefWidth(apWidth);
                ap.setMinWidth(apWidth);
                ap.setMaxWidth(apWidth);

                image.setFitWidth((double) newValue);
            }
        };
        ChangeListener<Number> heightResizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                final ImageView image = (ImageView) scene.lookup("#image");
                AnchorPane ap = (AnchorPane) scene.lookup("#anchor");

                double apWidthOffset = (double) newValue * 0.7077;
                double apWidth = scene.getWidth() - apWidthOffset;

                ap.setPrefWidth(apWidth);
                ap.setMinWidth(apWidth);
                ap.setMaxWidth(apWidth);
                image.setFitHeight((double) newValue);
            }
        };
        scene.widthProperty().addListener(widthResizeListener);
        scene.heightProperty().addListener(heightResizeListener);

//        if (primaryStage.getScene() != null) {
//            ap.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
//        }

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Login Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
