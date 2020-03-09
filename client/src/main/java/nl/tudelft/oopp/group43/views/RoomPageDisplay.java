package nl.tudelft.oopp.group43.views;

import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.components.BackButton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RoomPageDisplay {

    private Stage stage;
    private Scene scene;
    private JSONParser json;

    /**
     * Loads the Room Page for a specific building with all of its content.
     *
     * @param stage      The stage of the application we want to change the view of
     * @param buildingID The ID of the building of which we want the rooms
     * @throws IOException Throws an IOException if it cant find the FXML scene
     */
    public void start(Stage stage, String buildingID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/roomPageScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        this.stage = stage;
        this.scene = scene;
        /*
        Add the back button to the scene
         */
        BackButton btn = new BackButton();
        AnchorPane ap = (AnchorPane) scene.lookup("#root");
        ap.getChildren().add(btn.getBackButton());
        // A pre push of the buildingID of this roompage for later use
        BackButton.pushScene(buildingID);

        if (stage.getScene() != null) {
            ap.setPrefSize(stage.getWidth(), stage.getHeight() - 39);
        }

        stage.setScene(scene);
        stage.setTitle("Campus Management - Room Page");
        stage.show();

        JSONParser json = new JSONParser();
        this.json = json;

        try {
            String response = ServerCommunication.getRoomsFromBuilding(buildingID);
            GridPane roomList = (GridPane) scene.lookup("#room_list");
            Label load = new Label("Loading Rooms...");
            roomList.getChildren().add(0, load);

            /*
            Checks if the rooms are in JSONArray format or in JSONObject format.
            If it is something other than this it means there was an error with the server communication.
            If that happens it shows an 'useful' error :).
             */
            if (response.charAt(0) == '[') {
                JSONArray rooms = (JSONArray) json.parse(response);

                for (int i = 0; i < rooms.size(); i++) {
                    addRoom(rooms, roomList, i);

                    if (i == 0) {
                        load.setText("");
                    }
                }
            } else {
                load.setText("Oops, something went wrong,\nplease check your internet connection and try again");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a room label to the room list.
     *
     * @param rooms    A JSONArray containing all rooms retrieved from database
     * @param roomList The Gridpane to which the rooms will be added
     * @param i        index of the JSONArray
     */
    private void addRoom(JSONArray rooms, GridPane roomList, int i) {
        JSONObject obj = (JSONObject) rooms.get(i);

        Label label = new Label();
        label.setText((String) obj.get("room_name"));
        label.getStyleClass().add("rooms");
        label.setId(Long.toString((Long) obj.get("id")));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if (i > 0) {
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(30.0);
            rc.setMinHeight(30.0);
            rc.setMaxHeight(30.0);
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            roomList.getRowConstraints().add(rc);
        }

        try {
            addEventListener(label, obj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        roomList.add(label, 0, i);
    }

    /**
     * Adds an eventlistener to the room labels, that when clicked on load the room information page.
     *
     * @param label The label to which to add the event listener
     * @param obj   A JSONObject of the room
     * @throws ParseException Throws an parse exception when the room cant get parsed
     */
    private void addEventListener(Label label, JSONObject obj) throws ParseException {
        GridPane gp = (GridPane) scene.lookup("#parent");
        JSONObject info = (JSONObject) json.parse((String) obj.get("attributes"));

        label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AnchorPane ap = new AnchorPane();
                ap.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                ap.setMinSize(100.0, 100.0);

                // Creates the context
                String information = "Space type: " + info.get("spaceType")
                        + "\nChalkboard: " + info.get("chalkBoard")
                        + "\nWhiteboard: " + info.get("whiteBoard")
                        + "\nSmartboard: " + info.get("smartBoard")
                        + "\nBlinds: " + info.get("blinds")
                        + "\nDisplay Screen: " + info.get("display")
                        + "\nDesktop PC: " + info.get("desktopPc")
                        + "\nProjector: " + info.get("projector")
                        + "\nPower supply: " + info.get("powerSupply")
                        + "\nSurface area: " + info.get("surfaceArea")
                        + "\nSeat capacity: " + info.get("seatCapacity")
                        + "\nMicrophone: " + info.get("microphone")
                        + "\nSound-installation: " + info.get("soundInstallation")
                        + "\nWheelchair accessible: " + info.get("wheelChairAccessible");
                Label roomInfo = new Label(information);
                gp.heightProperty().addListener(resizeInformationFont(roomInfo));
                roomInfo.setFont(new Font("Arial", 12));
                AnchorPane.setLeftAnchor(roomInfo, 30.0);
                AnchorPane.setBottomAnchor(roomInfo, 40.0);
                AnchorPane.setTopAnchor(roomInfo, 80.0);
                ap.getChildren().add(roomInfo);

                // Creates a button for the reservation
                Button reservationButton = new Button("Reserve Room");
                reservationButton.getStyleClass().add("reservation_button");
                AnchorPane.setBottomAnchor(reservationButton, 10.0);
                AnchorPane.setLeftAnchor(reservationButton, 50.0);
                AnchorPane.setRightAnchor(reservationButton, 50.0);
                reservationButton.setOnAction(reservationButtonAction());
                ap.getChildren().add(reservationButton);

                // Creates the title text
                Label titleText = new Label(label.getText());
                titleText.setFont(new Font("Arial", 20));
                gp.heightProperty().addListener(resizeTitleFont(titleText));
                AnchorPane.setTopAnchor(titleText, 10.0);
                AnchorPane.setLeftAnchor(titleText, 30.0);
                AnchorPane.setRightAnchor(titleText, 30.0);
                ap.getChildren().add(titleText);

                if (gp.getChildren().size() > 1) {
                    gp.getChildren().remove(1);
                }
                gp.add(ap, 1, 0);

                ReservationConfig.setSelectedRoom(Long.parseLong(label.getId()));
            }

            private EventHandler<ActionEvent> reservationButtonAction() {
                return new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ReservationDisplay rd = new ReservationDisplay();
                        BackButton.pushScene("room");
                        try {
                            rd.start(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }

            private ChangeListener<Number> resizeInformationFont(Label label) {
                return new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double fontSize = (double)newValue * 0.0324;
                        label.setFont(new Font("Arial", fontSize));
                    }
                };
            }

            private ChangeListener<Number> resizeTitleFont(Label label) {
                return new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double fontSize = (double)newValue * 0.0541;
                        label.setFont(new Font("Arial", fontSize));
                    }
                };
            }
        });
    }

}
