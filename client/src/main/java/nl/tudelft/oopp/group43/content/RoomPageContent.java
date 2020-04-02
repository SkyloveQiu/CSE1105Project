package nl.tudelft.oopp.group43.content;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.*;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



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

    private static boolean adminAdded = false;
    private static ArrayList<CheckBox> checkBoxes;
    private static ArrayList<String> deleteRoomList = new ArrayList<>();
    private static ArrayList<Button> editButtons;
    private static String menu = "";

    private static ArrayList<String> buildings;
    private static Long buildingNumberAdd;

    /**
     * Adds the content to the room page.
     *
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
        addGridPaneSizeListener(list);

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
                hours.add("0" + i + ":00");
            } else {
                hours.add(i + ":00");
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(hours);
        fromTime.setItems(list);
        untilTime.setItems(list);

        addChoiceboxListener();
    }

    /**
     * Adds a width size listener to the specified gridpane that changes the location of the reserve button and extends the horizontal line.
     *
     * @param gridPane gridpane to add the listener to.
     */
    private static void addGridPaneSizeListener(GridPane gridPane) {
        gridPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (Node node : gridPane.getChildren()) {
                    Pane root = (Pane) node;
                    Button button = (Button) ((Pane) root.getChildren().get(0)).getChildren().get(1);
                    Line line = (Line) ((Pane) root.getChildren().get(0)).getChildren().get(5);

                    line.setEndX((double) newValue - 80);
                    button.setLayoutX((double) newValue - 220);
                }
                if (editButtons != null) {
                    for (Button editButton : editButtons) {
                        editButton.setPrefWidth(editButton.getWidth() + ((double) newValue - (double) oldValue));
                    }
                }
                //System.out.println(newValue);
            }
        });
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

        if (ServerCommunication.getRole().equals("admin")) {
            addAdmin();
        }
    }

    /**
     * Adds a single room instance to the list.
     *
     * @param obj the JSONObject containing the room.
     * @param i   the index in the list.
     */
    private static void addRoom(JSONObject obj, int i) {
        Pane root = new Pane();
        String id = Long.toString((long) obj.get("id"));
        id = id + ";" + ((JSONObject) obj.get("building")).get("building_number");
        root.setId(id);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Pane content = new Pane();
        content.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addRoomClickEvent(content, i);
        root.getChildren().add(content);

        Label name = new Label((String) obj.get("room_name"));
        name.setLayoutX(30);
        name.setLayoutY(30);
        name.setFont(new Font("Arial", 20));

        Button reserveButton = new Button("Reserve");
        reserveButton.setStyle("-fx-background-color: mediumseagreen;");
        reserveButton.setPrefSize(100, 30);
        reserveButton.setLayoutX(scene.getWidth() - 323);
        reserveButton.setLayoutY(33);
        addReservationButtonEvent(reserveButton, id);

        Label building = new Label((String) ((JSONObject) obj.get("building")).get("building_name"));
        building.setLayoutX(35);
        building.setLayoutY(55);
        building.setFont(new Font("Arial", 12));
        building.setTextFill(Color.FORESTGREEN);

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(1200);
        line.setStartY(100);
        line.setEndY(100);
        line.setVisible(false);
        Label info = new Label("test");
        info.setPrefHeight(400);
        info.setLayoutX(20.0);
        info.setLayoutY(100.0);
        info.setVisible(false);
        addInfo(info, obj);

        Label expanded = new Label("false");
        expanded.setVisible(false);

        content.getChildren().add(name);
        content.getChildren().add(reserveButton);
        content.getChildren().add(expanded);
        content.getChildren().add(building);
        content.getChildren().add(info);
        content.getChildren().add(line);
        content.setStyle("-fx-background-color: paleturquoise; -fx-background-radius: 20 20 20 20; -fx-border-color: black; -fx-border-radius: 20 20 20 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 3, 5)");
        content.setPrefHeight(100);
        content.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //System.out.println("add room: " + name.getText());
        list.add(root, 0, i);
    }

    /**
     * Adds information to the info label of a room.
     *
     * @param info the info label
     * @param obj  the json object of the room
     */
    private static void addInfo(Label info, JSONObject obj) {
        JSONParser json = new JSONParser();
        try {
            JSONObject attr = (JSONObject) json.parse((String) obj.get("attributes"));

            String information = "Space type: \t\t\t" + attr.get("spaceType")
                    + "\nChalkboard: \t\t\t" + attr.get("chalkBoard")
                    + "\nWhiteboard: \t\t\t" + attr.get("whiteBoard")
                    + "\nSmartboard: \t\t\t" + attr.get("smartBoard")
                    + "\nBlinds: \t\t\t\t" + attr.get("blinds")
                    + "\nDisplay Screen: \t\t" + attr.get("display")
                    + "\nDesktop PC: \t\t\t" + attr.get("desktopPc")
                    + "\nProjector: \t\t\t" + attr.get("projector")
                    + "\nPower supply: \t\t\t" + attr.get("powerSupply")
                    + "\nSurface area: \t\t\t" + attr.get("surfaceArea")
                    + "\nSeat capacity: \t\t\t" + attr.get("seatCapacity")
                    + "\nMicrophone: \t\t\t" + attr.get("microphone")
                    + "\nSound-installation: \t\t" + attr.get("soundInstallation")
                    + "\nWheelchair accessible: \t" + attr.get("wheelChairAccessible");

            info.setText(information);
            info.setFont(new Font("Arial", 18));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a click event to the room that when clicked on it expands or contracts.
     *
     * @param root the pane of the room.
     * @param id   the id in the list.
     */
    private static void addRoomClickEvent(Pane root, int id) {
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (RowConstraints rc : list.getRowConstraints()) {
                    rc.setMinHeight(100);
                }
                for (Node node : list.getChildren()) {
                    Pane pane = (Pane) node;
                    ((Pane) pane.getChildren().get(0)).setMinHeight(100);
                }
                if (((Label) root.getChildren().get(2)).getText().equals("false")) {
                    RowConstraints rc = list.getRowConstraints().get(id);
                    rc.setMinHeight(500);
                    root.setMinHeight(500);
                    for (Node n : list.getChildren()) {
                        Pane node = (Pane) n;
                        node = (Pane) node.getChildren().get(0);
                        for (int i = 4; i < node.getChildren().size(); i++) {
                            node.getChildren().get(i).setVisible(false);
                        }
                    }

                    for (int i = 4; i < root.getChildren().size(); i++) {
                        root.getChildren().get(i).setVisible(true);
                    }

                    ((Label) root.getChildren().get(2)).setText("true");

                } else {
                    for (Node n : list.getChildren()) {
                        Pane node = (Pane) n;
                        node = (Pane) node.getChildren().get(0);
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
     *
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
     * Adds the admin buttons for CRUD.
     */
    private static void addAdmin() {
        checkBoxes = new ArrayList<>();
        editButtons = new ArrayList<>();

        for (Node node : list.getChildren()) {
            Pane root = (Pane) node;
            CheckBox checkBox = new CheckBox();
            checkBox.setLayoutX(10);
            checkBox.setLayoutY(40);
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    System.out.println("ticked room: " + root.getId());
                    deleteRoomList.add(root.getId());
                } else {
                    for (int i = 0; i < deleteRoomList.size(); i++) {
                        if (deleteRoomList.get(i).equals(root.getId())) {
                            deleteRoomList.remove(i);
                        }
                    }
                    System.out.println("unticked room: " + root.getId());
                }
            });
            root.getChildren().add(checkBox);
            checkBoxes.add(checkBox);

            Button edit = new Button("Edit room");
            edit.setLayoutY(400);
            edit.setLayoutX(750);
            edit.getStyleClass().add("edit");
            edit.setMinSize(200, 75);
            edit.setVisible(false);
            editButtons.add(edit);
            ImageView img = new ImageView(new Image("/icons/edit-icon.png"));
            img.setFitHeight(25.0);
            img.setFitWidth(25.0);
            edit.setGraphic(img);
            ((Pane) root.getChildren().get(0)).getChildren().add(edit);
            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    scene.lookup("#grayBackground").setVisible(true);
                    scene.lookup("#addMenu").setVisible(false);
                    menu = "edit";

                    ((TextField) scene.lookup("#editRoomName")).setText(((Label) ((Pane) root.getChildren().get(0)).getChildren().get(0)).getText());
                    ((Label) scene.lookup("#editRoomNumber")).setText("Room being edited: " + root.getId().split(";")[0]);
                    ((Label) scene.lookup("#editId")).setText(root.getId());
                    updateFields((AnchorPane) scene.lookup("#editFields"), ((Label) ((Pane) root.getChildren().get(0)).getChildren().get(4)).getText());

                    AnchorPane editFields = (AnchorPane) scene.lookup("#editFields");
                    for (Node n : editFields.getChildren()) {
                        if (n.getId() != null && n.getId().contains("Check")) {
                            ((Label) n).setText("");
                        }
                    }
                    editFields = (AnchorPane) scene.lookup("#edit");
                    for (Node n : editFields.getChildren()) {
                        if (n.getId() != null && n.getId().contains("Check")) {
                            ((Label) n).setText("");
                        }
                    }

                    scene.lookup("#editMenu").setVisible(true);
                }
            });
        }

        if (!adminAdded) {
            adminAdded = true;
            ThreadLock lock = new ThreadLock();
            BuildingData buildingData = new BuildingData(lock);
            buildingData.run();

            ImageView delete = new ImageView();
            delete.setImage(new Image("/icons/delete-icon.png"));
            delete.setFitWidth(40);
            delete.setFitHeight(40);
            delete.setLayoutX(138);
            delete.setLayoutY(265);
            delete.setId("delete");
            ColorAdjust adjust = new ColorAdjust();
            Blend blend = new Blend(BlendMode.SRC_ATOP, adjust, new ColorInput(0, 0, 40, 40, Color.SLATEGREY));
            delete.setEffect(blend);
            delete.setCache(true);
            delete.setCacheHint(CacheHint.SPEED);
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, delete);
            delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    deleteRooms(event);

                }
            });

            Pane deleteHover = new Pane();
            deleteHover.setPrefSize(44, 44);
            deleteHover.setLayoutX(136);
            deleteHover.setLayoutY(263);
            deleteHover.getStyleClass().add("deleteHover");
            addHover(delete, deleteHover);
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, deleteHover);

            Label deselect = new Label("deselect all");
            deselect.setPrefSize(124, 40);
            deselect.setLayoutX(185);
            deselect.setLayoutY(265);
            deselect.getStyleClass().add("deselect");
            deselect.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                for (CheckBox cb : checkBoxes) {
                    cb.setSelected(false);
                }
            });
            Pane deselectHover = new Pane();
            deselectHover.setPrefSize(128, 44);
            deselectHover.setLayoutX(183);
            deselectHover.setLayoutY(263);
            deselectHover.getStyleClass().add("filler"); //this is a filler because otherwise the addHover method does not work XD
            addHover(deselect, deselectHover);
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, deselect);
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, deselectHover);

            Label seperator = new Label();
            seperator.setPrefSize(5, 44);
            seperator.setLayoutX(316);
            seperator.setLayoutY(263);
            seperator.getStyleClass().add("seperator");
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, seperator);

            ImageView add = new ImageView();
            add.setImage(new Image("/icons/add-icon.png"));
            add.setFitWidth(40);
            add.setFitHeight(40);
            add.setLayoutX(328);
            add.setLayoutY(265);
            add.setId("delete");
            add.setEffect(blend);
            add.setCache(true);
            add.setCacheHint(CacheHint.SPEED);
            add.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#grayBackground").setVisible(true);
                    scene.lookup("#editMenu").setVisible(false);

                    ChoiceBox <String> addBuildings = (ChoiceBox <String>) scene.lookup("#addBuildings");

                    buildingNumberAdd = Long.valueOf(-1);

                    int size = BuildingsConfig.getNumberBuildings();
                    buildings = new ArrayList<>();

                    for(int i =0; i<size; i++)
                    {
                        JSONObject obj = BuildingsConfig.getBuilding(i);
                        buildings.add((String) obj.get("building_name"));
                    }
                    addBuildings.setItems(FXCollections.observableArrayList(buildings));
                    addBuildings.getSelectionModel().selectFirst();
                    ChangeListener <String> buildingListener = new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            getBuilding(newValue);
                        }
                    };
                    addBuildings.getSelectionModel().selectedItemProperty().removeListener(buildingListener);
                    addBuildings.getSelectionModel().selectedItemProperty().addListener(buildingListener);



                    menu = "add";

                    AnchorPane addFields = (AnchorPane) scene.lookup("#addFields");
                    for (Node n : addFields.getChildren()) {
                        if (n instanceof TextField) {
                            ((TextField) n).setText("");
                        }
                        if (n.getId() != null && n.getId().contains("Check")) {
                            ((Label) n).setText("");
                        }
                    }
                    addFields = (AnchorPane) scene.lookup("#addFacilities");
                    for (Node n : addFields.getChildren()) {
                        if (n instanceof TextField) {
                            ((TextField) n).setText("");
                        }
                        if (n.getId() != null && n.getId().contains("Check")) {
                            ((Label) n).setText("");
                        }
                    }

                    scene.lookup("#addMenu").setVisible(true);
                }
            });
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, add);

            Pane addHover = new Pane();
            addHover.setPrefSize(44, 44);
            addHover.setLayoutX(326);
            addHover.setLayoutY(263);
            addHover.getStyleClass().add("deleteHover");
            addHover(add, addHover);
            ((AnchorPane) scene.lookup("#root")).getChildren().add(9, addHover);
        }
    }

    /**
     * This adds an event listener to the node where when hovered on the pane gets a styleclass.
     *
     * @param node the node that gets hovered
     * @param pane the pane that gets the styleclass
     */
    private static void addHover(Node node, Pane pane) {
        node.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                pane.getStyleClass().add("deleteHoverhover");
            }
        });
        node.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                pane.getStyleClass().remove(1);
            }
        });
    }

    /**
     * Updates the fields of the edit menu to the values of the room being edited.
     *
     * @param root the root of the fields in the edit menu
     * @param info the info of the room
     */
    private static void updateFields(AnchorPane root, String info) {
        String[] infoArr = info.split("\n");
        String[][] fields = new String[infoArr.length][];
        for (int i = 0; i < infoArr.length; i++) {
            fields[i] = infoArr[i].split("\t");
        }

        int index = 0;
        for (Node n : root.getChildren()) {
            if (n instanceof TextField) {
                ((TextField) n).setText(fields[index][fields[index].length - 1]);
                index++;
            }
        }
    }

    /**
     * Setter for the selected rooms arraylist.
     *
     * @param rooms the new arraylist.
     */
    public static void setSelectedRooms(ArrayList<JSONObject> rooms) {
        selectedRooms = rooms;
    }

    /**
     * Getter for the rooms in the database.
     *
     * @return JSONArray with the database rooms.
     */
    public static JSONArray getDatabaseRooms() {
        return databaseRooms;
    }

    /**
     * Getter for the hoursFrom.
     *
     * @return the String with the hoursFrom
     */
    public static String getHoursFrom() {
        return hoursFrom;
    }

    /**
     * Setter for the hoursFrom field.
     *
     * @param hoursFrom String with the new hoursFrom.
     */
    public static void setHoursFrom(String hoursFrom) {
        RoomPageContent.hoursFrom = hoursFrom;
    }

    /**
     * Getter for the hoursTil.
     *
     * @return the String with the hoursTil
     */
    public static String getHoursTil() {
        return hoursTil;
    }

    /**
     * Setter for the hoursTil field.
     *
     * @param hoursTil String with the new hoursTil.
     */
    public static void setHoursTil(String hoursTil) {
        RoomPageContent.hoursTil = hoursTil;
    }

    /**
     * Getter for the localDate.
     *
     * @return LocalDate
     */
    public static LocalDate getDate() {
        return date;
    }

    /**
     * Setter for the localDate.
     *
     * @param date the new LocalDate.
     */
    public static void setDate(LocalDate date) {
        RoomPageContent.date = date;
    }

    private static void deleteRooms(MouseEvent event) {
        // if(deleteRoomList.size() == 0)
        //   return;
        String verification = "OK";
        for (int i = 0; i < deleteRoomList.size(); i++) {
            String index = deleteRoomList.get(i);

            String message = ServerCommunication.sendDeleteRoom(index.split(";")[0]);
            if (!message.equals("OK")) {
                verification = "NOT OK";
                break;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (verification.equals("NOT OK")) {
            alert.setContentText("Something goes wrong during the procedure!" + "\n" + " Please try again!");
        } else {
            alert.setContentText("The operation has been successfully done!");
            reloadRooms();
        }
        alert.showAndWait();

    }

    /**
     * Reloads the rooms, after an operation is done.
     */
    public static void reloadRooms() {
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
        addRooms();
    }

    public static String getMenu() {
        return menu;
    }

    public static void setMenu(String menuValue) {
        menu = menuValue;
    }

    public static void setAdminAdd(boolean admin) {
        adminAdded = admin;

    }

    private static void  getBuilding(String name) {
       if(name == null)
           return;
        int size = BuildingsConfig.getNumberBuildings();

        long buildingId = -1;
        for(int i =0 ; i<size; i++)
        {
            JSONObject obj = BuildingsConfig.getBuilding(i);
            String buildingName = (String) obj.get("building_name");
            if(name.equals(buildingName))
            {
                 buildingId =  (Long) obj.get("building_number");
                 break;
            }
        }
        buildingNumberAdd =  buildingId;
    }

    public static long getBuildingNumberAdd() {
        return buildingNumberAdd;
    }



}