package nl.tudelft.oopp.group43.content;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.ReservationPageContent;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RoomPageContent {

    private static Scene scene;
    private static LocalDate date;
    private static String hoursFrom;
    private static String hoursTil;

    private static AnchorPane ap;
    private static GridPane list;
    private static DatePicker datepicker;
    private static ChoiceBox<String> fromTime;
    private static ChoiceBox<String> untilTime;

    private static JSONArray databaseRooms;
    private static ArrayList<JSONObject> selectedRooms;

    /**
     * Adds the content to the room page.
     * @param currentScene the current scene.
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;
        date = null;
        hoursFrom = "";
        hoursTil = "";

        ap = (AnchorPane) scene.lookup("#timeDateSelect");
        datepicker = (DatePicker) scene.lookup("#date");
        fromTime = (ChoiceBox<String>) scene.lookup("#fromTime");
        untilTime = (ChoiceBox<String>) scene.lookup("#untilTime");
        list = (GridPane) scene.lookup("#roomList");

        selectedRooms = new ArrayList<>();

        addChoiceboxContent();
        addDatepickerListener();
    }

    /**
     * Adds the content to the hour choiceboxes.
     */
    private static void addChoiceboxContent() {
        ArrayList<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + Integer.toString(i) + ":00");
            } else {
                hours.add(Integer.toString(i) + ":00");
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(hours);
        fromTime.setItems(list);
        untilTime.setItems(list);

        addChoiceboxListener();
    }

    /**
     * Adds listeners to the choiceboxes that set the hours when one is selected + gets the available rooms when all fields are inputted.
     */
    private static void addChoiceboxListener() {
        fromTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setHoursFrom(fromTime.getItems().get((Integer) number2));

                if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                    try {
                        JSONParser json = new JSONParser();
                        JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                        databaseRooms = rooms;
                        for (int i = 0; i < rooms.size(); i++) {
                            selectedRooms.add((JSONObject) rooms.get(i));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        untilTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setHoursTil(untilTime.getItems().get((Integer) number2));

                if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                    try {
                        JSONParser json = new JSONParser();
                        JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                        databaseRooms = rooms;
                        for (int i = 0; i < rooms.size(); i++) {
                            selectedRooms.add((JSONObject) rooms.get(i));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Adds a listener to the datepicker that sets the date when one is selected + gets the available rooms when all fields are inputted.
     */
    private static void addDatepickerListener() {
        datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            setDate(newValue);

            if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                try {
                    JSONParser json = new JSONParser();
                    JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                    databaseRooms = rooms;
                    for (int i = 0; i < rooms.size(); i++) {
                        selectedRooms.add((JSONObject) rooms.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds the rooms to the list.
     */
    public static void addRooms() {
        list = (GridPane) scene.lookup("#roomList");

        while (list.getRowConstraints().size() > 0) {
            list.getRowConstraints().remove(0);
        }
        while (list.getChildren().size() > 0) {
            list.getChildren().remove(0);
        }

        for (int i = 0; i < selectedRooms.size(); i++) {
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(100);
            rc.setVgrow(Priority.SOMETIMES);
            list.getRowConstraints().add(rc);

            JSONObject obj = selectedRooms.get(i);
            addRoom(obj, i);
        }
    }

    /**
     * Adds a single room instance to the list.
     * @param obj the JSONObject containing the room.
     * @param i the index in the list.
     */
    private static void addRoom(JSONObject obj, int i) {
        AnchorPane root = new AnchorPane();
        String id = Long.toString((long) obj.get("id"));
        root.setId(id);
        addRoomClickEvent(root, i);

        Label name = new Label((String) obj.get("room_name"));
        name.setLayoutX(30);
        name.setLayoutY(30);
        name.setFont(new Font("Arial", 20));

        Button reserveButton = new Button("Reserve");
        reserveButton.setStyle("-fx-background-color: mediumseagreen;");
        reserveButton.setPrefSize(100, 30);
        AnchorPane.setTopAnchor(reserveButton, 33.0);
        AnchorPane.setRightAnchor(reserveButton, 30.0);
        addReservationButtonEvent(reserveButton, id);

        Label building = new Label((String) ((JSONObject) obj.get("building")).get("building_name"));
        building.setLayoutX(35);
        building.setLayoutY(55);
        building.setFont(new Font("Arial", 12));
        building.setTextFill(Color.FORESTGREEN);

        Label info = new Label("test");
        info.setPrefHeight(100);
        info.setLayoutX(20.0);
        info.setLayoutY(100.0);
        info.setVisible(false);

        Label expanded = new Label("false");
        expanded.setVisible(false);

        root.getChildren().add(name);
        root.getChildren().add(reserveButton);
        root.getChildren().add(expanded);
        root.getChildren().add(building);
        root.getChildren().add(info);
        root.setStyle("-fx-background-color: paleturquoise; -fx-background-radius: 20 20 20 20; -fx-border-color: black; -fx-border-radius: 20 20 20 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 3, 5)");
        root.setPrefHeight(100);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //System.out.println("add room: " + name.getText());
        list.add(root, 0, i);
    }

    /**
     * Adds a click event to the room that when clicked on it expands or contracts.
     * @param root the pane of the room.
     * @param id the id in the list.
     */
    private static void addRoomClickEvent(Pane root, int id) {
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (((Label) root.getChildren().get(2)).getText().equals("false")) {
                    for (RowConstraints rc : list.getRowConstraints()) {
                        rc.setMinHeight(100);
                    }
                    RowConstraints rc = list.getRowConstraints().get(id);
                    rc.setMinHeight(500);
                    for (Node n : list.getChildren()) {
                        Pane node = (Pane) n;
                        for (int i = 4; i < node.getChildren().size(); i++) {
                            node.getChildren().get(i).setVisible(false);
                        }
                    }

                    for (int i = 4; i < root.getChildren().size(); i++) {
                        root.getChildren().get(i).setVisible(true);
                    }

                    ((Label) root.getChildren().get(2)).setText("true");

                } else {
                    for (RowConstraints rc : list.getRowConstraints()) {
                        rc.setMinHeight(100);
                    }
                    for (Node n : list.getChildren()) {
                        Pane node = (Pane) n;
                        for (int i = 4; i < node.getChildren().size(); i++) {
                            node.getChildren().get(i).setVisible(false);
                        }
                    }

                    ((Label) root.getChildren().get(2)).setText("false");
                }
            }
        });
    }

    /**
     * Adds an event to the reservation button.
     * @param btn the button to which the event will be added.
     */
    private static void addReservationButtonEvent(Button btn, String id) {
        btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!ServerCommunication.getToken().equals("invalid")) {
                    ArrayList selectedHours = ReservationConfig.getSelectedHours();
                    ReservationConfig.setSelectedRoom(Long.parseLong(id));

                    String response = "";

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
                    formatter.withZone(ZoneId.of("UTC"));
                    for (int i = 0; i < selectedHours.size(); i++) {
                        LocalDateTime startDate = LocalDateTime.parse((String) selectedHours.get(i), formatter);
                        LocalDateTime endDate = startDate.plusHours(1);

                        String startTime = startDate.toString() + ":00.000+0000";
                        String endTime = endDate.toString() + ":00.000+0000";
                        System.out.println(startTime);
                        response = ServerCommunication.reserveRoomForHour(startTime, endTime);
                    }

                    ReservationPageContent.setDateString("");
                } else {
                    SceneLoader.setScene("login");
                    SceneLoader sl = new SceneLoader();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("To reserve a room you must be logged in!\nYou will get redirected");
                    alert.showAndWait();

                    try {
                        sl.start((Stage) scene.getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Setter for the selected rooms arraylist.
     * @param rooms the new arraylist.
     */
    public static void setSelectedRooms(ArrayList<JSONObject> rooms) {
        selectedRooms = rooms;
    }

    /**
     * Getter for the rooms in the database.
     * @return JSONArray with the database rooms.
     */
    public static JSONArray getDatabaseRooms() {
        return databaseRooms;
    }

    /**
     * Setter for the localDate.
     * @param date the new LocalDate.
     */
    public static void setDate(LocalDate date) {
        RoomPageContent.date = date;
    }

    /**
     * Setter for the hoursFrom field.
     * @param hoursFrom String with the new hoursFrom.
     */
    public static void setHoursFrom(String hoursFrom) {
        RoomPageContent.hoursFrom = hoursFrom;
    }

    /**
     * Setter for the hoursTil field.
     * @param hoursTil String with the new hoursTil.
     */
    public static void setHoursTil(String hoursTil) {
        RoomPageContent.hoursTil = hoursTil;
    }

    /**
     * Getter for the hoursFrom.
     * @return the String with the hoursFrom
     */
    public static String getHoursFrom() {
        return hoursFrom;
    }

    /**
     * Getter for the hoursTil.
     * @return the String with the hoursTil
     */
    public static String getHoursTil() {
        return hoursTil;
    }

    /**
     * Getter for the localDate.
     * @return LocalDate
     */
    public static LocalDate getDate() {
        return date;
    }
}
