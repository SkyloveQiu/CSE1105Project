package nl.tudelft.oopp.group43.content;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FoodPageContent {

    private static Scene scene;
    private static JSONArray array;

    private static String selectedBuilding;

    /**
     * Add dynamic content to the food page.
     * @param currentScene the current scene
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addTime();
        addBuildings();
    }

    /**
     * Add the times to the time choice box.
     */
    private static void addTime() {
        ChoiceBox<String> timeBox = (ChoiceBox<String>) scene.lookup("#timeList");

        ArrayList<String> times = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            times.add(i + ":00:00");
        }

        timeBox.setItems(FXCollections.observableArrayList(times));
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

        buildingBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> selectBuilding(v, oldValue, newValue));
    }

    /**
     * Event that occurs when a building is selected.
     * @param v observable value of the choice box
     * @param oldValue old value of the choice box
     * @param newValue new value of the choice box
     */
    private static void selectBuilding(ObservableValue<? extends String> v, String oldValue, String newValue) {
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.get("building_name").equals(newValue)) {
                selectedBuilding = jsonObject.get("building_number").toString();
                break;
            }
        }
        removeFood();
        addFood();
    }

    /**
     * Removes all food from the food page.
     */
    private static void removeFood() {
        GridPane foodGrid = (GridPane) scene.lookup("#foodGrid");
        foodGrid.getChildren().clear();
        foodGrid.getRowConstraints().clear();
    }

    /**
     * Add all food items on the food page.
     */
    private static void addFood() {
        GridPane foodGrid = (GridPane) scene.lookup("#foodGrid");
        JSONParser jsonParser = new JSONParser();
        try {

            JSONArray foodItemsJson = (JSONArray) jsonParser.parse(ServerCommunication.getFoodByBuilding(selectedBuilding));

            ArrayList<JSONObject> foodItems = new ArrayList<>();
            for (Object obj : foodItemsJson) {
                foodItems.add((JSONObject) obj);
            }

            for (int i = 0; i < foodItems.size(); i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setPrefHeight(100);
                foodGrid.getRowConstraints().add(rowConstraints);


                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getStyleClass().add("foodLabels");
                anchorPane.setId(((JSONObject) foodItems.get(i).get("id")).get("foodProduct").toString());
                anchorPane.setMinSize(100, 100);
                anchorPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                JSONObject foodProduct = (JSONObject) foodItems.get(i).get("foodProduct");
                Label foodName = new Label(foodProduct.get("name").toString());
                foodName.getStyleClass().add("foodName");
                AnchorPane.setLeftAnchor(foodName, 40d);
                AnchorPane.setTopAnchor(foodName, 20d);

                Label foodDescription = new Label(foodProduct.get("description").toString());
                foodDescription.getStyleClass().add("foodDescription");
                AnchorPane.setLeftAnchor(foodDescription, 40d);
                AnchorPane.setTopAnchor(foodDescription, 55d);

                double price = Double.parseDouble(foodProduct.get("price").toString());
                DecimalFormat formatter = new DecimalFormat("0.00");
                Label foodPrice = new Label("€" + formatter.format(price));
                foodPrice.getStyleClass().add("foodPrice");
                AnchorPane.setRightAnchor(foodPrice, 275d);
                AnchorPane.setTopAnchor(foodPrice, 40d);

                Spinner<Integer> spinner = new Spinner<>();
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
                spinner.setValueFactory(valueFactory);
                AnchorPane.setRightAnchor(spinner, 40d);
                AnchorPane.setTopAnchor(spinner, 35d);

                anchorPane.getChildren().addAll(foodName, foodDescription, foodPrice, spinner);

                foodGrid.add(anchorPane, 0, i);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all food selected in the correct format.
     * @return Gets all food
     */
    public static ArrayList<String> getSelectedFood() {
        GridPane foodGrid = (GridPane) scene.lookup("#foodGrid");
        ArrayList<String> selectedFood = new ArrayList<>();
        System.out.println(foodGrid.getChildren().size());
        for (Object obj : foodGrid.getChildren()) {
            if (obj instanceof AnchorPane) {
                AnchorPane foodProduct = (AnchorPane) obj;

                String spinnerValue = "0";
                for (Object foodObj : foodProduct.getChildren()) {
                    if (foodObj instanceof Spinner) {
                        Spinner spinner = (Spinner) foodObj;
                        spinnerValue = spinner.getValue().toString();
                    }
                }

                if (!spinnerValue.equals("0")) {
                    selectedFood.add(selectedBuilding + "-" + foodProduct.getId() + "-" + spinnerValue);
                }
            }
        }

        return selectedFood;
    }

    /**
     * Gets the selected building.
     * @return selected building
     */
    public static String getSelectedBuilding() {
        return selectedBuilding;
    }

    /**
     * Gets the selected time.
     * @return selected time
     */
    public static String getSelectedTime() {
        ChoiceBox<String> timeBox = (ChoiceBox<String>) scene.lookup("#timeList");
        return timeBox.getValue();
    }
}