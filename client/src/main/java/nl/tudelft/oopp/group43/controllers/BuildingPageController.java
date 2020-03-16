package nl.tudelft.oopp.group43.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.content.BuildingPageContent;

public class BuildingPageController {

    @FXML
    private TextField searchBar;

    /**
     * Searches for the building that contains the provided search query.
     *
     * @param event Event passed by the box when clicked on
     */
    @FXML
    private void searchBuildings(MouseEvent event) {
        String searchQuery = searchBar.getText();
        if (searchQuery != null) {

            Label[] buildings = BuildingPageContent.getLabelArr();
            ArrayList<Label> newBuildings = new ArrayList<>();

            for (int i = 0; i < buildings.length; i++) {
                if (StringChecker.containsIgnoreCase(searchQuery, buildings[i].getText())) {
                    newBuildings.add(buildings[i]);
                }
            }

            BuildingPageContent.setLabelArr(newBuildings);
            System.out.println("search");
            BuildingPageContent.addBuildings();
        } else {
            System.out.println("search");
            BuildingPageContent.add();
        }
    }

}
