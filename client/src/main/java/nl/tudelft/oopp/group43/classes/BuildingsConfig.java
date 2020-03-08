package nl.tudelft.oopp.group43.classes;

import java.util.List;

import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BuildingsConfig {

    private static int buildingColumnCount = 2;
    private static Label[] label;
    private static boolean accordionExpanded = false;
    private static double accordionHeight;
    private static List<JSONObject> listBuildings;

    /**
     * Gets the column amount of the building GridPane.
     *
     * @return the column amount
     */
    public static int getColumnCount() {
        return buildingColumnCount;
    }

    /**
     * Set the current column count.
     *
     * @param columns the current column count
     */
    public static void setColumnCount(int columns) {
        buildingColumnCount = columns;
    }

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

    /**
     * Gives the current value of the Accordion.
     *
     * @return true when the accordion is expanded, false if otherwise
     */
    public static boolean isAccordionExpanded() {
        return accordionExpanded;
    }

    /**
     * Sets the expanded value of the Accordion.
     *
     * @param b the current position of the Accordion (expanded or not)
     */
    public static void setAccordionExpanded(boolean b) {
        accordionExpanded = b;
    }

    /**
     * Gets the height of the expanded Accordion.
     *
     * @return a double with the height
     */
    public static double getAccordionHeight() {
        return accordionHeight;
    }

    /**
     * Sets the height of the expanded Accordion.
     *
     * @param accordionHeight a double with the height
     */
    public static void setAccordionHeight(double accordionHeight) {
        BuildingsConfig.accordionHeight = accordionHeight;
    }

    public static JSONObject getBuilding(int i) {
        return  listBuildings.get(i);
    }

    public static void setListBuildings(List<JSONObject> list) {
        listBuildings = list;
    }

}
