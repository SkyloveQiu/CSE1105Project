package nl.tudelft.oopp.group43.classes;

import java.util.List;

import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BuildingsConfig {

    private static Label[] label;
    private static List<JSONObject> listBuildings;

    /**
     * Gets the array with all building Labels.
     *
     * @return the array with all building Labels
     */
    public static Label[] getLabel() {
        return label;
    }

    /**
     * Sets the array of building Labels.
     *
     * @param labelArr the array with all building Labels
     */
    public static void setLabel(Label[] labelArr) {
        label = labelArr;
    }

    public static JSONObject getBuilding(int i) {
        return  listBuildings.get(i);
    }

    public static void setListBuildings(List<JSONObject> list) {
        listBuildings = list;
    }

    /**
     * Returns the number of the buildings
     * @return - an integer which represents the number of the buildings.
     */
    public static int getNumberBuildings(){ return listBuildings.size();}

}
