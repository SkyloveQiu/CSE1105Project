package nl.tudelft.oopp.group43.sceneloader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.components.SideBarMenu;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import nl.tudelft.oopp.group43.content.RegisterPageContent;
import nl.tudelft.oopp.group43.content.RoomPageContent;

import java.io.IOException;
import java.net.URL;

public class SceneLoader extends Application {

    private static String sceneString = "main";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlURL;

        switch (sceneString) {
            case "building":
                URL buildingURL = getClass().getResource("/buildingPage-overhaul.fxml");
                loader.setLocation(buildingURL);
                Parent root = loader.load();
                Scene scene = new Scene(root);

                SideBarMenu menu = new SideBarMenu(scene);
                AnchorPane parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                BackButton.pushScene("building");
                BackButton btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                BuildingPageContent.addContent(scene);

                primaryStage.setScene(scene);
                break;
            case "room":
                URL roomURL = getClass().getResource("/roomPage-overhaul.fxml");
                loader.setLocation(roomURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                BackButton.pushScene("room");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                RoomPageContent.addContent(scene);

                primaryStage.setScene(scene);
                break;
            case "bike":
                URL bikeURL = getClass().getResource("/bikePage.fxml");
                loader.setLocation(bikeURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                BackButton.pushScene("bike");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

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

                BackButton.pushScene("food");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

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

                BackButton.pushScene("calendar");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

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

                BackButton.pushScene("login");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                break;
            case "register":
                URL registerURL = getClass().getResource("/registerPage-overhaul.fxml");
                loader.setLocation(registerURL);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size()-1, menu.getRoot());

                BackButton.pushScene("register");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                RegisterPageContent.addContent(scene);

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

                BackButton.pushScene("profile");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

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

                BackButton.pushScene("main");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                break;
        }

        Scene scene = primaryStage.getScene();

        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        primaryStage.setMinHeight(ap.getPrefHeight());
        primaryStage.setMinWidth(ap.getPrefWidth());

        primaryStage.show();

        System.out.println("moved to: " + this.sceneString);
    }

    public static void setScene(String newScene) {
        sceneString = newScene;
    }
}
