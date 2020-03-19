package nl.tudelft.oopp.group43.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class BikePageContent {

    private static double windowHeight = 800;

    public static void addContent(Scene scene) {
        Button rentButton = (Button) scene.lookup("#rent");
        Button reserveButton = (Button) scene.lookup("#reserve");
        GridPane.setHalignment(rentButton, HPos.RIGHT);
        GridPane.setHalignment(reserveButton, HPos.CENTER);

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setWindowHeight((double) newValue);
            }
        });

        ChoiceBox<String> returnBuildingList = (ChoiceBox<String>) scene.lookup("#returnBuildingList");
        GridPane reserveBikeBuildingList = (GridPane) scene.lookup("#reserveBikeBuildingList");
        GridPane rentBikeBuildingList = (GridPane) scene.lookup("#rentBikeBuildingList");
        reserveBikeBuildingList.setVgap(20);
        rentBikeBuildingList.setVgap(20);
        JSONParser json = new JSONParser();
        try {
            JSONArray array = (JSONArray) json.parse(ServerCommunication.getBuilding());
            ArrayList<String> buildings = new ArrayList<String>();
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                buildings.add((String) jsonObject.get("building_name"));
            }

            returnBuildingList.setItems(FXCollections.observableArrayList(buildings));

            for (int i = 0; i < buildings.size(); i++) {
                RowConstraints rc = new RowConstraints();
                rc.setPrefHeight(50);
                reserveBikeBuildingList.getRowConstraints().add(rc);
                rentBikeBuildingList.getRowConstraints().add(rc);

                Label labelRent = new Label(buildings.get(i));
                labelRent.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                labelRent.getStyleClass().add("buildingLabels");
                rentBikeBuildingList.add(labelRent, 0, i);

                Label labelReserve = new Label(buildings.get(i));
                labelReserve.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                labelReserve.getStyleClass().add("buildingLabels");
                reserveBikeBuildingList.add(labelReserve, 0, i);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void setWindowHeight(double height) {
        windowHeight = height;
    }

    public static double getWindowHeight() {
        return windowHeight;
    }
}
