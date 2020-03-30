package nl.tudelft.oopp.group43.sceneloader;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.tudelft.oopp.group43.classes.ThreadLock;
import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.components.SideBarMenu;
import nl.tudelft.oopp.group43.content.BikePageContent;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import nl.tudelft.oopp.group43.content.CalendarPageContent;
import nl.tudelft.oopp.group43.content.FoodPageContent;
import nl.tudelft.oopp.group43.content.MainPageContent;
import nl.tudelft.oopp.group43.content.MyReservationsPageContent;
import nl.tudelft.oopp.group43.content.ProfilePageContent;
import nl.tudelft.oopp.group43.content.RegisterPageContent;
import nl.tudelft.oopp.group43.content.RoomPageContent;


public class SceneLoader extends Application {

    private static String sceneString = "main";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        RoomPageContent.setAdminAdd(false);

        switch (sceneString) {
            case "building":
                URL buildingUrl = getClass().getResource("/buildingPage-overhaul.fxml");
                loader.setLocation(buildingUrl);
                Parent root = loader.load();
                Scene scene = new Scene(root);

                SideBarMenu menu = new SideBarMenu(scene);
                AnchorPane parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("building");
                BackButton btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                AnchorPane ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();

                BuildingPageContent.addContent(scene);
                break;
            case "room":
                URL roomUrl = getClass().getResource("/roomPage-overhaul.fxml");
                loader.setLocation(roomUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("room");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();

                RoomPageContent.addContent(scene);
                break;
            case "bike":
                URL bikeUrl = getClass().getResource("/bikePage.fxml");
                loader.setLocation(bikeUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("bike");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();

                BikePageContent.addContent(scene);
                break;
            case "food":
                URL foodUrl = getClass().getResource("/foodPage.fxml");
                loader.setLocation(foodUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("food");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();
                FoodPageContent.addContent(scene);
                break;
            case "calendar":
                URL calendarUrl = getClass().getResource("/calendarPage.fxml");
                loader.setLocation(calendarUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene, "calendar");
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("calendar");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"), "calendar");

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();
                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        if (CalendarPageContent.areEntriesAddedAndNotSaved()) {
                            CalendarPageContent.saveEntries();
                            while (ThreadLock.flag != 0) {
                                Alert waitForSave = new Alert(Alert.AlertType.WARNING);
                                waitForSave.setContentText("Please wait for the calendar to save all entries of the calendar!");
                                waitForSave.showAndWait();
                            }
                        }
                    }
                });
                CalendarPageContent.addContent(scene);
                break;
            case "login":
                URL loginUrl = getClass().getResource("/loginPage-overhaul.fxml");
                loader.setLocation(loginUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("login");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();
                break;
            case "register":
                URL registerUrl = getClass().getResource("/registerPage-overhaul.fxml");
                loader.setLocation(registerUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("register");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();

                RegisterPageContent.addContent(scene);
                break;
            case "profile":
                URL profileUrl = getClass().getResource("/profilePage.fxml");
                loader.setLocation(profileUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("profile");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();
                ProfilePageContent.addContent(scene);
                break;

            case "myreservations":
                URL reservationsUrl = getClass().getResource("/myReservationsPage.fxml");
                loader.setLocation(reservationsUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("myreservations");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();
                MyReservationsPageContent.addContent(scene);
                break;
            default:
                URL xmlUrl = getClass().getResource("/mainPage-overhaul.fxml");
                loader.setLocation(xmlUrl);
                root = loader.load();
                scene = new Scene(root);

                menu = new SideBarMenu(scene);
                parent = (AnchorPane) scene.lookup("#root");
                parent.getChildren().add(parent.getChildren().size() - 1, menu.getRoot());

                BackButton.pushScene("main");
                btn = new BackButton((ImageView) scene.lookup("#back_arrow"));

                primaryStage.setScene(scene);
                ap = (AnchorPane) scene.lookup("#root");
                primaryStage.setMinHeight(ap.getPrefHeight());
                primaryStage.setMinWidth(ap.getPrefWidth());

                primaryStage.show();

                MainPageContent.addContent(scene);
                break;
        }

        //Scene scene = primaryStage.getScene();
        //
        //AnchorPane ap = (AnchorPane) scene.lookup("#root");
        //primaryStage.setMinHeight(ap.getPrefHeight());
        //primaryStage.setMinWidth(ap.getPrefWidth());
        //
        //primaryStage.show();

        System.out.println("moved to: " + this.sceneString);
    }

    public static void setScene(String newScene) {
        sceneString = newScene;
    }

}
