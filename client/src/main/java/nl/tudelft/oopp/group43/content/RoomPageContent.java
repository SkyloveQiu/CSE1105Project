package nl.tudelft.oopp.group43.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.util.ArrayList;

public class RoomPageContent {

    private static Scene scene;
    private static LocalDate date;
    private static String hoursFrom;
    private static String hoursTil;

    private AnchorPane ap;
    private static GridPane list;
    private DatePicker datepicker;
    private ChoiceBox<String> fromTime;
    private ChoiceBox<String> untilTime;

    private static JSONArray databaseRooms;
    private static ArrayList<JSONObject> selectedRooms;

    public RoomPageContent(Scene currentScene) {
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
    }

    public void addContent() {
        addChoiceboxContent();
        addDatepickerListener();
    }

    private void addChoiceboxContent() {
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

    private void addChoiceboxListener() {
        fromTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setHoursFrom(fromTime.getItems().get((Integer) number2));

                if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                    ap.setVisible(false);
                    try {
                        addRooms();
                        JSONParser json = new JSONParser();
                        JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                        databaseRooms = rooms;
                        for(int i = 0; i < rooms.size(); i++) {
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
                    ap.setVisible(false);
                    try {
                        JSONParser json = new JSONParser();
                        JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                        databaseRooms = rooms;
                        for(int i = 0; i < rooms.size(); i++) {
                            selectedRooms.add((JSONObject) rooms.get(i));
                        }
                        addRooms();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void addDatepickerListener() {
        datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            setDate(newValue);

            if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                ap.setVisible(false);
                try {
                    JSONParser json = new JSONParser();
                    JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRooms());
                    databaseRooms = rooms;
                    for(int i = 0; i < rooms.size(); i++) {
                        selectedRooms.add((JSONObject) rooms.get(i));
                    }
                    addRooms();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void addRooms() throws ParseException {
        JSONParser json = new JSONParser();

        while(list.getRowConstraints().size() > 0) {
            list.getRowConstraints().remove(0);
            //System.out.println("remove row " + list.getRowConstraints().remove(0));
            if(list.getChildren().size() > 0) {
                list.getChildren().remove(0);
                //System.out.println("remove child " + list.getChildren().remove(0));
            }
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

    private static void addRoom(JSONObject obj, int i) {
        Pane root = new  Pane();
        String id = Long.toString((long) obj.get("id"));
        root.setId(id);
        addRoomClickEvent(root, Integer.parseInt(id));

        Label name = new Label((String) obj.get("room_name"));
        name.setLayoutX(30);
        name.setLayoutY(30);
        name.setFont(new Font("Arial", 20));

        Button reserveButton = new Button("Reserve");
        reserveButton.setStyle("-fx-background-color: mediumseagreen;");
        reserveButton.setPrefSize(100, 30);
        reserveButton.setLayoutX(930);
        reserveButton.setLayoutY(33);
        addReservationButtonEvent(reserveButton);

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
        root.setPrefHeight(200);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        list.add(root, 0, i);
    }

    private static void addRoomClickEvent(Pane root, int id) {
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(((Label) root.getChildren().get(2)).getText().equals("false")) {
                    for (RowConstraints rc : list.getRowConstraints()) {
                        rc.setMinHeight(100);
                    }
                    RowConstraints rc = list.getRowConstraints().get(id);
                    rc.setMinHeight(200);
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

                    ((Label) root.getChildren().get(1)).setText("false");
                }
            }
        });
    }

    private static void addReservationButtonEvent(Button btn) {
        
    }

    public static void setSelectedRooms(ArrayList<JSONObject> rooms) {
        selectedRooms = rooms;
    }

    public static JSONArray getDatabaseRooms() {
        return databaseRooms;
    }

    public static void setDate(LocalDate date) {
        RoomPageContent.date = date;
    }

    public static void setHoursFrom(String hoursFrom) {
        RoomPageContent.hoursFrom = hoursFrom;
    }

    public static void setHoursTil(String hoursTil) {
        RoomPageContent.hoursTil = hoursTil;
    }
}
