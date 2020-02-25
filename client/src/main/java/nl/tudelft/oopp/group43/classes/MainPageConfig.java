package nl.tudelft.oopp.group43.classes;

import javafx.scene.control.Label;

public class MainPageConfig {

    private static int buildingColumnCount = 2;
    private static Label[] label;

    public static int getColumnCount() {
        return buildingColumnCount;
    }

    public static void setColumnCount(int columns) {
        buildingColumnCount = columns;
    }

    public static void setLabel(Label[] labelArr) {
        label = labelArr;
    }

    public static Label[] getLabel() {
        return label;
    }

}
