package nl.tudelft.oopp.group43.content;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class FoodPageContent {

    private static Scene scene;

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addBuildings();
    }

    private static void addBuildings() {
        ChoiceBox<String> returnBuildingList = (ChoiceBox<String>) scene.lookup("#returnBuildingList");
        JSONParser json = new JSONParser();
        try {
            JSONArray array = (JSONArray) json.parse(ServerCommunication.getBuilding());
            ArrayList<String> buildings = new ArrayList<String>();
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                buildings.add((String) jsonObject.get("building_name"));
            }

            returnBuildingList.setItems(FXCollections.observableArrayList(buildings));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}