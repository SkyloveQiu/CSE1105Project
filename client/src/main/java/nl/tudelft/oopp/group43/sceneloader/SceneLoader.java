package nl.tudelft.oopp.group43.sceneloader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.SideBarMenu;
import nl.tudelft.oopp.group43.content.MainPageContent;
import nl.tudelft.oopp.group43.content.RoomPageContent;

import java.io.IOException;
import java.net.URL;

public class SceneLoader extends Application {

    private static String scene = "main";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlURL;

        switch (scene) {
            case "building":
                URL buildingURL = getClass().getResource("/buildingPage.fxml");
                loader.setLocation(buildingURL);
                Parent root = loader.load();
                Scene scene = new Scene(root);

                SideBarMenu menu = new SideBarMenu(scene);
                AnchorPane parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "room":
                URL roomURL = getClass().getResource("/roomPage-overhaul.fxml");
                loader.setLocation(roomURL);
                root = loader.load();
                scene = new Scene(root);
                primaryStage.setScene(scene);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                RoomPageContent rpc = new RoomPageContent(scene);
                rpc.addContent();

                break;
            case "bike":
                URL bikeURL = getClass().getResource("/bikePage.fxml");
                loader.setLocation(bikeURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "food":
                URL foodURL = getClass().getResource("/foodPage.fxml");
                loader.setLocation(foodURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "calendar":
                URL calendarURL = getClass().getResource("/calendarPage.fxml");
                loader.setLocation(calendarURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "login":
                URL loginURL = getClass().getResource("/loginPage-overhaul.fxml");
                loader.setLocation(loginURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "register":
                URL registerURL = getClass().getResource("/RegisterScene-overhaul.fxml");
                loader.setLocation(registerURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            case "profile":
                URL profileURL = getClass().getResource("/profilePage.fxml");
                loader.setLocation(profileURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
            default:
                URL xmlUrl = getClass().getResource("/mainPage-overhaul.fxml");
                loader.setLocation(xmlUrl);
                root = loader.load();
                scene = new Scene(root);

                MainPageContent.addContent(scene);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                primaryStage.setScene(scene);
                break;
        }

        Scene scene = primaryStage.getScene();

        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        primaryStage.setMinHeight(ap.getPrefHeight());
        primaryStage.setMinWidth(ap.getPrefWidth());

        primaryStage.show();

        System.out.println("moved to: " + this.scene);
    }

    public static void setScene(String newScene) {
        scene = newScene;
    }
}
