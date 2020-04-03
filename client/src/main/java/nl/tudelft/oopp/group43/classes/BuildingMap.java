package nl.tudelft.oopp.group43.classes;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildingMap {

    private static Map buildings;
    private static ArrayList<JSONObject> arr;

    public static boolean isNull() {
        return buildings == null;
    }

    public static void init() {
        buildings = new HashMap();
        arr = new ArrayList();
    }

    public static void put(long id, JSONObject value) {
        buildings.put(id, value);
        arr.add(value);
    }

    public static JSONObject get(long id) {
        return (JSONObject) buildings.get(id);
    }

    public static ArrayList<JSONObject> getAll() {
        return arr;
    }

}
