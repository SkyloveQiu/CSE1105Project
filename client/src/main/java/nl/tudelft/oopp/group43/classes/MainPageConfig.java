package nl.tudelft.oopp.group43.classes;

import javafx.scene.control.Label;

public class MainPageConfig {

    private static int buildingColumnCount = 2;
    private static Label[] label;

    /**
     * Gets the column amount of the building GridPane
     * @return the column amount
     */
    public static int getColumnCount() {
        return buildingColumnCount;
    }

    /**
     * Set the current column count
     * @param columns the current column count
     */
    public static void setColumnCount(int columns) {
        buildingColumnCount = columns;
    }

    /**
     * Sets the array of building Labels
     * @param labelArr the array with all building Labels
     */
    public static void setLabel(Label[] labelArr) {
        label = labelArr;
    }

    /**
     * Gets the array with all building Labels
     * @return the array with all building Labels
     */
    public static Label[] getLabel() {
        return label;
    }

}
