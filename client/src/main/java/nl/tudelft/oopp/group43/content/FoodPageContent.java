package nl.tudelft.oopp.group43.content;

import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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

    private static ArrayList<String> selectedFood;

    /**
     * Add dynamic content to the food page.
     * @param currentScene the current scene
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addBuildings();
        addFood();
    }

    /**
     * Adds the rooms for a specific building to the choice box on the food page.
     * @param buildingName the building name
     */
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

    /**
     * Add the buildings to the choice box on the food page.
     */
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

    /**
     * Add all food items on the food page.
     */
    private static void addFood() {
        selectedFood = new ArrayList<>();

        GridPane foodGrid = (GridPane) scene.lookup("#foodGrid");
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray foodItemsJson = (JSONArray) jsonParser.parse(ServerCommunication.getFood());

            ArrayList<JSONObject> foodItems = new ArrayList<>();
            for (Object obj : foodItemsJson) {
                foodItems.add((JSONObject) obj);
            }

            for (int i = 0; i < foodItems.size(); i++) {
                if (i % 2 == 0) {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setPrefHeight(100);
                    foodGrid.getRowConstraints().add(rowConstraints);
                }

                Label label = new Label(foodItems.get(i).get("name") + " €" + foodItems.get(i).get("price") + "\n" + foodItems.get(i).get("description"));
                label.setId(foodItems.get(i).get("id").toString());
                label.setMinSize(100, 100);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.getStyleClass().add("foodLabels");

                foodGrid.add(label, i % 2, i / 2);
                addSelectEvent(label);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the selection events when a food item is clicked.
     * @param label the label that needs an event.
     */
    private static void addSelectEvent(Label label) {
        label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (label.getStyleClass().size() > 2) {
                    selectedFood.remove(selectedFood.indexOf(label.getId()));
                    label.getStyleClass().remove(2);
                } else {
                    selectedFood.add(label.getId());
                    label.getStyleClass().add("selected_food");
                }
            }
        });
        label.setCursor(Cursor.HAND);
    }

    public static ArrayList<String> getSelectedFood() {
        return selectedFood;
    }
}