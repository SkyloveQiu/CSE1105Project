package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import org.json.simple.JSONObject;

public class BuildingPageController {

    @FXML
    private TextField searchBar;

    @FXML
    private TextField addBuildingNumber;
    @FXML
    private Label addNumberCheck;
    @FXML
    private TextField addBuildingName;
    @FXML
    private TextField addBuildingAddress;
    @FXML
    private Label addMondayMsg;
    @FXML
    private Label addTuesdayMsg;
    @FXML
    private Label addWednesdayMsg;
    @FXML
    private Label addThursdayMsg;
    @FXML
    private Label addFridayMsg;
    @FXML
    private Label addSaturdayMsg;
    @FXML
    private Label addSundayMsg;
    @FXML
    private Label addAddressCheck;
    @FXML
    private Label addNameCheck;

    @FXML
    private Label editMsg;
    @FXML
    private Label editBuildingNumber;
    @FXML
    private TextField editBuildingName;
    @FXML
    private TextField editBuildingAddress;
    @FXML
    private Label editMondayMsg;
    @FXML
    private Label editTuesdayMsg;
    @FXML
    private Label editWednesdayMsg;
    @FXML
    private Label editThursdayMsg;
    @FXML
    private Label editFridayMsg;
    @FXML
    private Label editSaturdayMsg;
    @FXML
    private Label editSundayMsg;
    @FXML
    private Label editAddressCheck;
    @FXML
    private Label editNameCheck;

    @FXML
    private Label deleteMsg;
    @FXML
    private ScrollPane deleteBuildingList;

    @FXML
    private Pane grayBackground;
    @FXML
    private GridPane editMenu;
    @FXML
    private GridPane addMenu;
    @FXML
    private GridPane deleteMenu;

    /**
     * Searches for the building that contains the provided search query.
     */
    @FXML
    private void searchBuildings() {
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
            BuildingPageContent.add();
        }
    }

    /*
    =====================================================================
    METHODS FOR ADD
     */

    @FXML
    private void showAddMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        addMenu.setVisible(true);
    }

    /**
     * If you press the add building button, the method will try to do this operation, it it will be possible.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void addConfirm(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean ok = addCheckNumber();
        ok = addCheckAddress() && ok;
        ok = addCheckName() && ok;
        if (addMondayMsg.getText().isEmpty() && addTuesdayMsg.getText().isEmpty() && addWednesdayMsg.getText().isEmpty() && addThursdayMsg.getText().isEmpty() && addFridayMsg.getText().isEmpty()
                && addSaturdayMsg.getText().isEmpty() && addSundayMsg.getText().isEmpty() && ok) {

            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", getAddHoursDay(stage, "monday"));
            openingHours.put("tu", getAddHoursDay(stage, "tuesday"));
            openingHours.put("we", getAddHoursDay(stage, "wednesday"));
            openingHours.put("th", getAddHoursDay(stage, "thursday"));
            openingHours.put("fr", getAddHoursDay(stage, "friday"));
            openingHours.put("sa", getAddHoursDay(stage, "saturday"));
            openingHours.put("su", getAddHoursDay(stage, "sunday"));

            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(addBuildingNumber.getText()));
            building.put("building_name", addBuildingName.getText());
            building.put("address", addBuildingAddress.getText());
            building.put("opening_hours", openingHours.toString());
            String a = ServerCommunication.sendAddBuilding(building);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (a.equals("OK")) {
                alert.setContentText("The building was successfully added.");
            } else {
                alert.setContentText("A building with this number already exists!!! \nPlease change the building number!");
            }
            alert.showAndWait();
        }
    }

    /**
     * Checks if the user put a number which is grater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean addCheckNumber() {
        if (addBuildingNumber.getText().isEmpty()) {
            addNumberCheck.setText("You cannot have this field empty");
            return false;
        }
        try {
            String nunmberString = addBuildingNumber.getText();
            System.out.println(Long.MAX_VALUE);
            long number = Long.valueOf(nunmberString);
            addNumberCheck.setText("");
            return true;
        } catch (Exception e) {
            addNumberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
            return false;
        }
    }

    /**
     * Gets the information regarding the opening hours of a day for a building.
     *
     * @param stage - the actual stage
     * @param day   - string with the day of the week
     * @return - the information regarding the opening hours of that day
     */
    @SuppressWarnings("unchecked")
    private String getAddHoursDay(Stage stage, String day) {
        ChoiceBox<String> openH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> openM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> closeH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> closeM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseM");

        String hours = openH.getSelectionModel().getSelectedItem() + ":" + openM.getSelectionModel().getSelectedItem();
        hours = hours + "-" + closeH.getSelectionModel().getSelectedItem() + ":" + closeM.getSelectionModel().getSelectedItem();

        if (hours.contains("--") == true) {
            hours = "closed";
        }
        return hours;
    }

    /**
     * Checks if the Building Name field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean addCheckName() {
        if (addBuildingName.getText().isEmpty()) {
            addNameCheck.setText("You cannot have this field empty");
            return false;
        } else {
            addNameCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the Address field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean addCheckAddress() {
        if (addBuildingAddress.getText().isEmpty()) {
            addAddressCheck.setText("You cannot have this field empty");
            return false;
        } else {
            addAddressCheck.setText("");
            return true;
        }
    }

    /*
    =====================================================================
    METHODS FOR EDIT
     */

    @FXML
    private void showEditMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        editMenu.setVisible(true);
    }

    /**
     * If you press the edit building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) {

        if (editMsg.getText().isEmpty()) {
            editMsg.setText("You have not selected a building!");
            return;
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (editMondayMsg.getText().isEmpty() && editTuesdayMsg.getText().isEmpty() && editWednesdayMsg.getText().isEmpty() && editThursdayMsg.getText().isEmpty()
                && editFridayMsg.getText().isEmpty() && editSaturdayMsg.getText().isEmpty() && editSundayMsg.getText().isEmpty() && editNameCheck.getText().isEmpty() && editAddressCheck.getText().isEmpty()) {

            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", getEditHoursDay(stage, "monday"));
            openingHours.put("tu", getEditHoursDay(stage, "tuesday"));
            openingHours.put("we", getEditHoursDay(stage, "wednesday"));
            openingHours.put("th", getEditHoursDay(stage, "thursday"));
            openingHours.put("fr", getEditHoursDay(stage, "friday"));
            openingHours.put("sa", getEditHoursDay(stage, "saturday"));
            openingHours.put("su", getEditHoursDay(stage, "sunday"));

            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(editBuildingNumber.getText()));
            building.put("building_name", editBuildingName.getText());
            building.put("address", editBuildingAddress.getText());
            building.put("opening_hours", openingHours.toString());

            String a = ServerCommunication.sendEditBuilding(building);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (a.equals("OK")) {
                alert.setContentText("The building was successfully edited.");
            } else {
                alert.setContentText("NOT OK");
            }
            alert.showAndWait();
        }

    }


    /**
     * Gets the information regarding the opening hours of a day for a building.
     *
     * @param stage - the actual stage
     * @param day   - string with the day of the week
     * @return - the information regarding the opening hours of that day
     */
    @SuppressWarnings("unchecked")
    private String getEditHoursDay(Stage stage, String day) {
        ChoiceBox<String> openH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> openM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> closeH = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> closeM = (ChoiceBox<String>) stage.getScene().lookup("#" + day + "CloseM");

        String hours = openH.getSelectionModel().getSelectedItem() + ":" + openM.getSelectionModel().getSelectedItem();
        hours = hours + "-" + closeH.getSelectionModel().getSelectedItem() + ":" + closeM.getSelectionModel().getSelectedItem();

        if (hours.contains("--") == true) {
            hours = "closed";
        }
        return hours;
    }

    /**
     * Checks if the Building Name field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editCheckName() {
        if (editBuildingName.getText().isEmpty()) {
            editNameCheck.setText("You cannot have this field empty");
        } else {
            editNameCheck.setText("");
        }
    }

    /**
     * Checks if the Address field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editCheckAddress() {
        if (editBuildingAddress.getText().isEmpty()) {
            editAddressCheck.setText("You cannot have this field empty");
        } else {
            editAddressCheck.setText("");
        }
    }

    /*
    =====================================================================
    METHODS FOR DELETE
     */

    @FXML
    private void showDeleteMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        deleteBuildingList.setContent(null);
        deleteMenu.setVisible(true);
    }

    /**
     * If you press the delete building button, the method will check if you selected a building and do the delete operation.
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void deleteConfirm(ActionEvent event) {

        if (deleteMsg.getText() == null) {
            deleteMsg.setText("You have not selected a building!");
            return;
        }


        String a = ServerCommunication.sendDeleteBuilding(getBuildingID());
        deleteMsg.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (a.equals("OK")) {
            alert.setContentText("The building was successfully deleted.");
        } else {
            alert.setContentText("NOT OK");
        }
        alert.showAndWait();
    }

    /**
     * Analyse the delete message, it extracts the id of the building.
     * @return - the id of the building which is selected.
     */
    private String getBuildingID() {
        String sentence = deleteMsg.getText();
        String[] words = sentence.split("\\s+");
        String buildingId = words[9];
        buildingId = buildingId.substring(0, buildingId.length() - 1);

        return buildingId;
    }
}
