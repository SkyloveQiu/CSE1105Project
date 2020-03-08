package nl.tudelft.oopp.group43.classes;

import java.util.ArrayList;

public class ReservationConfig {

    private static long selectedBuilding;
    private static long selectedRoom;
    private static ArrayList selectedHours = new ArrayList();

    /**
     * Getter for the selected building id.
     * @return the selected building id
     */
    public static long getSelectedBuilding() {
        return selectedBuilding;
    }

    /**
     * Setter for the selected building id.
     * @param building the selected building id
     */
    public static void setSelectedBuilding(long building) {
        selectedBuilding = building;
    }

    /**
     * Getter for the selected room id.
     * @return the selected room id
     */
    public static long getSelectedRoom() {
        return selectedRoom;
    }

    /**
     * Setter for the selected room id.
     * @param room the selected room id
     */
    public static void setSelectedRoom(long room) {
        selectedRoom = room;
    }

    /**
     * Adds an hour to the selected hours in format of: yyyy-MM-dd-hh.
     * @param hour the hour to add to the selection
     */
    public static void addHour(String hour) {
        System.out.println("add " + hour);
        selectedHours.add(hour);
    }

    /**
     * Removes a specific hour from the selected hours.
     * @param hour hour to remove
     * @return true if successful false if otherwise
     */
    public static boolean removeHour(String hour) {
        System.out.println("remove " + hour);

        for (int i = 0; i < selectedHours.size(); i++) {
            if (selectedHours.get(i).equals(hour)) {
                selectedHours.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the selected hours.
     */
    public static void resetSelectedHours() {
        System.out.println("reset");
        selectedHours = new ArrayList();
    }

    /**
     * Returns the selected hours for reserving these hours.
     * @return
     */
    public static ArrayList getSelectedHours() {
        return selectedHours;
    }

}
