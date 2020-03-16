package nl.tudelft.oopp.group43.classes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FilterContent {

    private Stage stage;
    private Scene scene;

    /**
     * Constructor for the FilterContent class.
     *
     * @param stage - the actual stage.
     */
    public FilterContent(Stage stage) {
        this.stage = stage;
        scene = stage.getScene();
    }

    /**
     * Takes the rooms from the server with the chosen attributes.
     */
    public void getRoomsFilter(String blinds, String desktop, String projector, String chalkBoard, String microphone, String smartBoard, String whiteBoard, String powerSupply,
                               String soundInstallation, String wheelChair, String space) {

        JSONParser json = new JSONParser();

        try {

            String response = ServerCommunication.getBuildingFilter(blinds, desktop, projector, chalkBoard, microphone, smartBoard, whiteBoard, powerSupply,
                    soundInstallation, wheelChair, space);
            GridPane roomList = (GridPane) scene.lookup("#room_list");
            while (!roomList.getChildren().isEmpty()) {
                roomList.getChildren().remove(0);
                if (roomList.getRowConstraints().size() > 0) {
                    roomList.getRowConstraints().remove(0);
                }
            }

            Label load = new Label("Loading Rooms");
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


        String text = (String) obj.get("room_name");

        try {
            JSONParser parser = new JSONParser();
            JSONObject building = (JSONObject) parser.parse(String.valueOf(obj.get("building")));
            text = text + " - " + building.get("building_name");

        } catch (Exception e) {
            e.printStackTrace();
        }


        // System.out.println(obj);
        //System.out.println(obj.get("building"));
        Label label = new Label();

        label.setText(text);
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

        //add listener

        roomList.add(label, 0, i);
    }


}
