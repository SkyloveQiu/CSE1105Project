package nl.tudelft.oopp.group43.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class BuildingMap {

    private static Map<Long, JSONObject> buildings;
    private static ArrayList<JSONObject> arr;
    private static Map<Long, Long> buildingOfRoom;

    public static boolean isNull() {
        return buildings == null;
    }

    /**
     * Initializes all fields of this class.
     */
    public static void init() {
        buildings = new HashMap<Long, JSONObject>();
        arr = new ArrayList();
        buildingOfRoom = new HashMap<Long, Long>();
    }

    public static void put(long id, JSONObject value) {
        buildings.put(id, value);
        arr.add(value);
    }

    public static JSONObject get(long id) {
        return buildings.get(id);
    }

    public static ArrayList<JSONObject> getAll() {
        return arr;
    }

    public static JSONObject getBuildingOfRoom(long id) {
        return buildings.get(buildingOfRoom.get(id));
    }

    public static void setBuildingToRoom(long buildingId, long roomId) {
        buildingOfRoom.put(roomId, buildingId);
    }

}
