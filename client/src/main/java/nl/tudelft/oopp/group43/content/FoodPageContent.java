package nl.tudelft.oopp.group43.content;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Collections;

public class FoodPageContent {

    private static Scene scene;
    private static JSONArray array;

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addBuildings();
    }

    private static void addRooms(String building_name) {
        ChoiceBox<String> roomsBox = (ChoiceBox<String>) scene.lookup("#returnRoomsList");
        long buildingNo = -1;
        for (Object obj: array){
            JSONObject jsonObject = (JSONObject) obj;
            if (building_name.equals(jsonObject.get("building_name"))){
                buildingNo = (long) jsonObject.get("building_number");
            }

        }
        JSONParser json = new JSONParser();
        try {
            JSONArray arrayRoom = (JSONArray) json.parse(ServerCommunication.getRoomsFromBuilding(Long.toString(buildingNo)));
            ArrayList<String> rooms = new ArrayList<String>();
            for (Object obj : arrayRoom) {
                JSONObject jsonObject = (JSONObject) obj;
                rooms.add((String) jsonObject.get("room_name"));
            }
            Collections.sort(rooms);
            roomsBox.setItems(FXCollections.observableArrayList(rooms));
            System.out.println("This line is run.");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void addBuildings() {
        ChoiceBox<String> buildingBox = (ChoiceBox<String>) scene.lookup("#returnBuildingList");
        JSONParser json = new JSONParser();
        try {
            array = (JSONArray) json.parse(ServerCommunication.getBuilding());
            ArrayList<String> buildings = new ArrayList<String>();
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                buildings.add((String) jsonObject.get("building_name"));
            }

            buildingBox.setItems(FXCollections.observableArrayList(buildings));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        buildingBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> addRooms(newValue));

    }
}