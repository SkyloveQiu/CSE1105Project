package nl.tudelft.oopp.group43.classes;

public class ReservationConfig {

    private static long selectedBuilding;
    private static long selectedRoom;

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
}
