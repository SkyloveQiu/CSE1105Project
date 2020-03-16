package nl.tudelft.oopp.group43.components;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

import java.io.IOException;

public class SideBarMenu {

    private AnchorPane root;
    private Label building;
    private Label rooms;
    private Label bikes;
    private Label food;
    private Label calendar;
    private Label profile;

    private final double layoutXExpanded = 0.0;
    private final double layoutXContracted = -228.0;
    private boolean expanded;
    private boolean allowedToContract;

    /**
     * Constructor of the slidable sidebar
     *
     * @param scene scene to which the sidebar will be added.
     */
    public SideBarMenu(Scene scene) {
        expanded = false;
        allowedToContract = false;

        root = new AnchorPane();
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        root.setLayoutX(layoutXContracted);
        root.setPrefSize(316.0, 800.0);
        root.setStyle("-fx-background-color: #0d7fa5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0);");

        Font font = new Font("Arial", 30);

        ImageView backIcon = (ImageView) scene.lookup("#back_arrow");
        addImageEvent(backIcon);

        building = new Label("Buildings");
        building.setFont(font);
        building.setLayoutX(120);
        building.setPrefSize(135, 63);
        AnchorPane.setTopAnchor(building, 91.0);
        addLabelEvent(building, "building");
        ImageView buildingIcon = (ImageView) scene.lookup("#buildingIcon");
        addImageEvent(buildingIcon);
        addSceneSwitchEvent(buildingIcon, "building");

        rooms = new Label("Rooms");
        rooms.setFont(font);
        rooms.setLayoutX(120);
        rooms.setPrefSize(135, 63);
        AnchorPane.setTopAnchor(rooms, 168.0);
        addLabelEvent(rooms, "room");
        ImageView roomIcon = (ImageView) scene.lookup("#roomIcon");
        addImageEvent(roomIcon);
        addSceneSwitchEvent(roomIcon, "room");

        bikes = new Label("Bikes");
        bikes.setFont(font);
        bikes.setLayoutX(120);
        bikes.setPrefSize(135, 63);
        AnchorPane.setTopAnchor(bikes, 245.0);
        addLabelEvent(bikes, "bike");
        ImageView bikeIcon = (ImageView) scene.lookup("#bikeIcon");
        addImageEvent(bikeIcon);
        addSceneSwitchEvent(bikeIcon, "bike");

        food = new Label("Food");
        food.setFont(font);
        food.setLayoutX(120);
        food.setPrefSize(135, 63);
        AnchorPane.setTopAnchor(food, 322.0);
        addLabelEvent(food, "food");
        ImageView foodIcon = (ImageView) scene.lookup("#foodIcon");
        addImageEvent(foodIcon);
        addSceneSwitchEvent(foodIcon, "food");

        calendar = new Label("Calendar");
        calendar.setFont(font);
        calendar.setLayoutX(120);
        calendar.setPrefSize(135, 63);
        AnchorPane.setTopAnchor(calendar, 399.0);
        addLabelEvent(calendar, "calendar");
        ImageView calendarIcon = (ImageView) scene.lookup("#calendarIcon");
        addImageEvent(calendarIcon);
        addSceneSwitchEvent(calendarIcon, "calendar");

        profile = new Label("Profile");
        profile.setFont(font);
        profile.setLayoutX(120);
        profile.setPrefSize(135, 63);
        AnchorPane.setBottomAnchor(profile, 14.0);
        ImageView userIcon = (ImageView) scene.lookup("#userIcon");
        addImageEvent(userIcon);
        if (ServerCommunication.getToken().equals("invalid")) {
            profile.setText("Sign-In");
            addLabelEvent(profile, "login");
            addSceneSwitchEvent(userIcon, "login");
        } else {
            addLabelEvent(profile, "profile");
            addSceneSwitchEvent(userIcon, "profile");
        }

        root.getChildren().addAll(building, rooms, bikes, food, calendar, profile);

        AnchorPane menu = (AnchorPane) scene.lookup("#side_bar");

        menu.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!expanded && root.getLayoutX() == layoutXContracted) {
                    expanded = true;

                    TranslateTransition trans = new TranslateTransition(Duration.seconds(1), root);
                    trans.setFromX(layoutXContracted);
                    trans.setToX(layoutXExpanded);
                    trans.setCycleCount(1);

                    trans.play();

                    menu.setStyle("-fx-background-color: #0d7fa5;");
                    root.setLayoutX(layoutXExpanded);
                }
            }
        });

        root.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (expanded && event.getSceneX() > 88) {
                    expanded = false;

                    TranslateTransition trans = new TranslateTransition(Duration.seconds(1), root);
                    trans.setFromX(layoutXExpanded);
                    trans.setToX(layoutXContracted);
                    trans.setCycleCount(1);

                    trans.setOnFinished(e -> root.setLayoutX(layoutXContracted));
                    trans.play();

                    menu.setStyle("-fx-background-color: #0d7fa5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0);");
                }
            }
        });
    }

    /**
     * Adds a hover effect to the image.
     *
     * @param image image to which to add the effect
     */
    private void addImageEvent(ImageView image) {
        ColorAdjust adjust = new ColorAdjust();
        Blend hover = new Blend(BlendMode.SRC_ATOP, adjust, new ColorInput(0, 0, 63, 63, Color.color(0.196, 0.196, 0.196)));
        Blend exit = new Blend(BlendMode.SRC_ATOP, adjust, new ColorInput(0, 0, 63, 63, Color.POWDERBLUE));

        image.effectProperty().bind(Bindings.when(image.hoverProperty()).then((Effect) hover).otherwise(exit));
        image.setCache(true);
        image.setCacheHint(CacheHint.SPEED);
    }

    /**
     * Adds a scene switch to the node when clicked on.
     *
     * @param node  node to which to add the event
     * @param event event type AKA string with the name of the scene
     */
    private void addSceneSwitchEvent(Node node, String event) {
        node.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                SceneLoader.setScene(event);
                SceneLoader sl = new SceneLoader();
                try {
                    sl.start((Stage) ((Node) e.getSource()).getScene().getWindow());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds a hover event to the label + adds a click event.
     *
     * @param label label to which to add the events
     * @param event event type AKA string with the name of the scene
     */
    private void addLabelEvent(Label label, String event) {
        addSceneSwitchEvent(label, event);

        label.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setStyle("-fx-text-fill: powderblue");
            }
        });

        label.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setStyle("-fx-text-fill: #323232");
            }
        });
    }

    /**
     * Getter for the Sidebar, this will be added to the scene.
     *
     * @return AnchorPane with the sidebar content.
     */
    public AnchorPane getRoot() {
        return root;
    }
}
