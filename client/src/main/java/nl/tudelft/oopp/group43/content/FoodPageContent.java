package nl.tudelft.oopp.group43.content;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FoodPageContent {

    private static Scene scene;
    private static JSONArray array;

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addBuildings();
        addFood();
    }

    private static void addRooms(String buildingName) {
        ChoiceBox<String> roomsBox = (ChoiceBox<String>) scene.lookup("#returnRoomsList");
        long buildingNo = -1;
        for (Object obj: array) {
            JSONObject jsonObject = (JSONObject) obj;
            if (buildingName.equals(jsonObject.get("building_name"))) {
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

        buildingBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> addRooms(newValue));

    }

    private static void addFood() {
        GridPane foodGrid = (GridPane) scene.lookup("#foodGrid");
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray foodItemsJson = (JSONArray) jsonParser.parse(ServerCommunication.getFood());
            System.out.println(foodItemsJson.toJSONString());

            ArrayList<String> foodItems = new ArrayList<>();
            for (Object obj : foodItemsJson) {
                foodItems.add(((JSONObject) obj).toJSONString());
            }

            for (int i = 0; i < foodItems.size(); i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPrefHeight(50);
                foodGrid.getRowConstraints().add(rowConstraints);

                Label label = new Label(foodItems.get(i));
                label.setId(foodItems.get(i));
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //label.getStyleClass().add("foodLabels");

                foodGrid.add(label, 0, i);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}