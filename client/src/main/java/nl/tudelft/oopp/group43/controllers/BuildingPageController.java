package nl.tudelft.oopp.group43.controllers;

import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.BuildDataScene;
import nl.tudelft.oopp.group43.classes.BuildDeletePageContent;
import nl.tudelft.oopp.group43.classes.BuildingData;
import nl.tudelft.oopp.group43.classes.BuildingEditPageContent;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.classes.ThreadLock;
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
    private TextField addMondayHours;
    @FXML
    private TextField addTuesdayHours;
    @FXML
    private TextField addWednesdayHours;
    @FXML
    private TextField addThursdayHours;
    @FXML
    private TextField addFridayHours;
    @FXML
    private TextField addSaturdayHours;
    @FXML
    private TextField addSundayHours;

    @FXML
    private Label editMsg;
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
    private TextField editMondayHours;
    @FXML
    private TextField editTuesdayHours;
    @FXML
    private TextField editWednesdayHours;
    @FXML
    private TextField editThursdayHours;
    @FXML
    private TextField editFridayHours;
    @FXML
    private TextField editSaturdayHours;
    @FXML
    private TextField editSundayHours;
    @FXML
    private Label editId;

    @FXML
    private Label deleteMsg;
    @FXML
    private ScrollPane deleteBuildingList;
    @FXML
    private Label deleteId;

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
    private void showAddMenu() {
        grayBackground.setVisible(true);
        editMenu.setVisible(false);
        removeEditFields();
        deleteMenu.setVisible(false);
        deleteId.setText("");
        addMenu.setVisible(true);

        addTextFieldListener(addMondayHours);
        addTextFieldListener(addTuesdayHours);
        addTextFieldListener(addWednesdayHours);
        addTextFieldListener(addThursdayHours);
        addTextFieldListener(addFridayHours);
        addTextFieldListener(addSaturdayHours);
        addTextFieldListener(addSundayHours);
    }

    @FXML
    private void closeAddMenu() {
        grayBackground.setVisible(false);
        addMenu.setVisible(false);
        removeAddFields();
        clearAddCheckFields();
    }

    /**
     * If you press the add building button, the method will try to do this operation, it it will be possible.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void addConfirm(ActionEvent event) {
        boolean ok = addCheckFields();
        if (ok) {
            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", addMondayHours.getText());
            openingHours.put("tu", addTuesdayHours.getText());
            openingHours.put("we", addWednesdayHours.getText());
            openingHours.put("th", addThursdayHours.getText());
            openingHours.put("fr", addFridayHours.getText());
            openingHours.put("sa", addSaturdayHours.getText());
            openingHours.put("su", addSundayHours.getText());

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
            removeAddFields();
            BuildingPageContent.add();
        }
    }

    /**
     * Checks if the user has provided every field + correct input for each field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean addCheckFields() {
        boolean ok = true;
        if (!addTestField("addBuildingNumber", "addBuildingName", "addBuildingAddress", "addMondayHours", "addTuesdayHours", "addWednesdayHours",
                "addThursdayHours", "addFridayHours", "addSaturdayHours", "addSundayHours")) {
            ok = false;
        }
        return ok;
    }

    private void clearAddCheckFields() {
        addNumberCheck.setText("");
        addNameCheck.setText("");
        addAddressCheck.setText("");
        addMondayMsg.setText("");
        addTuesdayMsg.setText("");
        addWednesdayMsg.setText("");
        addThursdayMsg.setText("");
        addFridayMsg.setText("");
        addSaturdayMsg.setText("");
        addSundayMsg.setText("");
    }

    @FXML
    private void addCheckField(Event e) {
        addTestField(((Node) e.getSource()).getId());
    }

    private boolean addTestField(String... names) {
        boolean ok = true;
        for (int i = 0; i < names.length; i++) {
            switch (names[i]) {
                case "addBuildingNumber":
                    if (addBuildingNumber.getText().isEmpty()) {
                        addNumberCheck.setText("You cannot have this field empty");
                        ok = false;
                    } else {
                        addNumberCheck.setText("");
                    }
                    try {
                        boolean valid = Long.parseLong(addBuildingNumber.getText()) >= 0;
                        if (!valid) {
                            addNumberCheck.setText("Please provide a correct building ID");
                            ok = false;
                        }
                    } catch (Exception e) {
                        addNumberCheck.setText("Please provide a correct building ID");
                        ok = false;
                    }
                    break;
                case "addBuildingName":
                    if (addBuildingName.getText().isEmpty()) {
                        addNameCheck.setText("You cannot have this field empty");
                        ok = false;
                    } else {
                        addNameCheck.setText("");
                    }
                    break;
                case "addBuildingAddress":
                    if (addBuildingAddress.getText().isEmpty()) {
                        addAddressCheck.setText("You cannot have this field empty");
                        ok = false;
                    } else {
                        addAddressCheck.setText("");
                    }
                    break;
                case "addMondayHours":
                    if (addMondayHours.getText().isEmpty() || (addMondayHours.getText().length() < 11 && !addMondayHours.getText().equals("closed"))) {
                        addMondayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addMondayMsg.setText("");
                    }
                    break;
                case "addTuesdayHours":
                    if (addTuesdayHours.getText().isEmpty() || (addTuesdayHours.getText().length() < 11 && !addTuesdayHours.getText().equals("closed"))) {
                        addTuesdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addTuesdayMsg.setText("");
                    }
                    break;
                case "addWednesdayHours":
                    if (addWednesdayHours.getText().isEmpty() || (addWednesdayHours.getText().length() < 11 && !addWednesdayHours.getText().equals("closed"))) {
                        addWednesdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addWednesdayMsg.setText("");
                    }
                    break;
                case "addThursdayHours":
                    if (addThursdayHours.getText().isEmpty() || (addThursdayHours.getText().length() < 11 && !addThursdayHours.getText().equals("closed"))) {
                        addThursdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addThursdayMsg.setText("");
                    }
                    break;
                case "addFridayHours":
                    if (addFridayHours.getText().isEmpty() || (addFridayHours.getText().length() < 11 && !addFridayHours.getText().equals("closed"))) {
                        addFridayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addFridayMsg.setText("");
                    }
                    break;
                case "addSaturdayHours":
                    if (addSaturdayHours.getText().isEmpty() || (addSaturdayHours.getText().length() < 11 && !addSaturdayHours.getText().equals("closed"))) {
                        addSaturdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addSaturdayMsg.setText("");
                    }
                    break;
                case "addSundayHours":
                    if (addSundayHours.getText().isEmpty() || (addSundayHours.getText().length() < 11 && !addSundayHours.getText().equals("closed"))) {
                        addSundayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        addSundayMsg.setText("");
                    }
                    break;
                default:
                    break;
            }
        }

        return ok;
    }

    /**
     * Removes the input in the fields of the menu.
     */
    private void removeAddFields() {
        addBuildingNumber.setText("");
        addBuildingName.setText("");
        addBuildingAddress.setText("");
        addMondayHours.setText("");
        addTuesdayHours.setText("");
        addWednesdayHours.setText("");
        addThursdayHours.setText("");
        addFridayHours.setText("");
        addSaturdayHours.setText("");
        addSundayHours.setText("");
    }

    /*
    =====================================================================
    METHODS FOR EDIT
     */

    @FXML
    private void showEditMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        addMenu.setVisible(false);
        removeAddFields();
        deleteMenu.setVisible(false);
        deleteId.setText("");
        editMenu.setVisible(true);

        addTextFieldListener(editMondayHours);
        addTextFieldListener(editTuesdayHours);
        addTextFieldListener(editWednesdayHours);
        addTextFieldListener(editThursdayHours);
        addTextFieldListener(editFridayHours);
        addTextFieldListener(editSaturdayHours);
        addTextFieldListener(editSundayHours);

        addEditBuildings((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    private void closeEditMenu() {
        grayBackground.setVisible(false);
        editMenu.setVisible(false);
        removeEditFields();
        clearEditCheckFields();
    }


    /**
     * If you press the edit building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) {

        if (editId.getText().isEmpty()) {
            editMsg.setText("You have not selected a building!");
            return;
        }

        if (editCheckFields()) {

            JSONObject openingHours = new JSONObject();
            openingHours.put("mo", editMondayHours.getText());
            openingHours.put("tu", editTuesdayHours.getText());
            openingHours.put("we", editWednesdayHours.getText());
            openingHours.put("th", editThursdayHours.getText());
            openingHours.put("fr", editFridayHours.getText());
            openingHours.put("sa", editSaturdayHours.getText());
            openingHours.put("su", editSundayHours.getText());

            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(editId.getText()));
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
            BuildingPageContent.add();
            removeEditFields();
            addEditBuildings((Stage) ((Node) event.getSource()).getScene().getWindow());
        }

    }

    /**
     * Checks if the user has provided every field + correct input for each field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean editCheckFields() {
        boolean ok = true;
        if (!editTestField("editBuildingName", "editBuildingAddress", "editMondayHours", "editTuesdayHours", "editWednesdayHours",
                "editThursdayHours", "editFridayHours", "editSaturdayHours", "editSundayHours")) {
            ok = false;
        }
        return ok;
    }

    @FXML
    private void editCheckField(Event e) {
        editTestField(((Node) e.getSource()).getId());
    }

    private boolean editTestField(String... names) {
        boolean ok = true;
        for (int i = 0; i < names.length; i++) {
            switch (names[i]) {
                case "editBuildingName":
                    if (editBuildingName.getText().isEmpty()) {
                        editNameCheck.setText("You cannot have this field empty");
                        ok = false;
                    } else {
                        editNameCheck.setText("");
                    }
                    break;
                case "editBuildingAddress":
                    if (editBuildingAddress.getText().isEmpty()) {
                        editAddressCheck.setText("You cannot have this field empty");
                        ok = false;
                    } else {
                        editAddressCheck.setText("");
                    }
                    break;
                case "editMondayHours":
                    if (editMondayHours.getText().isEmpty() || (editMondayHours.getText().length() < 11 && !editMondayHours.getText().equals("closed"))) {
                        editMondayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editMondayMsg.setText("");
                    }
                    break;
                case "editTuesdayHours":
                    if (editTuesdayHours.getText().isEmpty() || (editTuesdayHours.getText().length() < 11 && !editTuesdayHours.getText().equals("closed"))) {
                        editTuesdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editTuesdayMsg.setText("");
                    }
                    break;
                case "editWednesdayHours":
                    if (editWednesdayHours.getText().isEmpty() || (editWednesdayHours.getText().length() < 11 && !editWednesdayHours.getText().equals("closed"))) {
                        editWednesdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editWednesdayMsg.setText("");
                    }
                    break;
                case "editThursdayHours":
                    if (editThursdayHours.getText().isEmpty() || (editThursdayHours.getText().length() < 11 && !editThursdayHours.getText().equals("closed"))) {
                        editThursdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editThursdayMsg.setText("");
                    }
                    break;
                case "editFridayHours":
                    if (editFridayHours.getText().isEmpty() || (editFridayHours.getText().length() < 11 && !editFridayHours.getText().equals("closed"))) {
                        editFridayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editFridayMsg.setText("");
                    }
                    break;
                case "editSaturdayHours":
                    if (editSaturdayHours.getText().isEmpty() || (editSaturdayHours.getText().length() < 11 && !editSaturdayHours.getText().equals("closed"))) {
                        editSaturdayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editSaturdayMsg.setText("");
                    }
                    break;
                case "editSundayHours":
                    if (editSundayHours.getText().isEmpty() || (editSundayHours.getText().length() < 11 && !editSundayHours.getText().equals("closed"))) {
                        editSundayMsg.setText("Please provide a correct time frame.");
                        ok = false;
                    } else {
                        editSundayMsg.setText("");
                    }
                    break;
                default:
                    break;
            }
        }

        return ok;
    }

    private void clearEditCheckFields() {
        editNameCheck.setText("");
        editAddressCheck.setText("");
        editMondayMsg.setText("");
        editTuesdayMsg.setText("");
        editWednesdayMsg.setText("");
        editThursdayMsg.setText("");
        editFridayMsg.setText("");
        editSaturdayMsg.setText("");
        editSundayMsg.setText("");
    }

    /**
     * Adds all buildings as content using a new Thread.
     * Doing this removes initial startup lag.
     *
     * @param stage Stage is passed as parameter to get all Nodes
     */
    private void addEditBuildings(Stage stage) {
        ThreadLock lock = new ThreadLock();

        BuildingData data = new BuildingData(lock);
        Thread threadData = new Thread(data);
        threadData.setDaemon(true);
        threadData.start();

        BuildDataScene editPage = new BuildingEditPageContent(stage, lock);
        Thread threadEditBuildingPage = new Thread(editPage);
        threadEditBuildingPage.setDaemon(true);
        threadEditBuildingPage.start();

    }

    /**
     * Removes the input in the fields of the menu.
     */
    private void removeEditFields() {
        editBuildingName.setText("");
        editBuildingAddress.setText("");
        editMondayHours.setText("");
        editTuesdayHours.setText("");
        editWednesdayHours.setText("");
        editThursdayHours.setText("");
        editFridayHours.setText("");
        editSaturdayHours.setText("");
        editSundayHours.setText("");
        editId.setText("");
    }

    /*
    =====================================================================
    METHODS FOR DELETE
     */

    @FXML
    private void showDeleteMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        addMenu.setVisible(false);
        removeAddFields();
        editMenu.setVisible(false);
        removeEditFields();
        deleteBuildingList.setContent(null);
        deleteMenu.setVisible(true);

        addDeleteBuildings((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    private void closeDeleteMenu() {
        grayBackground.setVisible(false);
        deleteMenu.setVisible(false);
        deleteId.setText("");
    }


    /**
     * If you press the delete building button, the method will check if you selected a building and do the delete operation.
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void deleteConfirm(ActionEvent event) {

        if (deleteId.getText().length() < 1) {
            deleteMsg.setText("You have not selected a building!");
            return;
        }


        String a = ServerCommunication.sendDeleteBuilding(deleteId.getText());
        deleteMsg.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (a.equals("OK")) {
            alert.setContentText("The building was successfully deleted.");
        } else {
            alert.setContentText("NOT OK");
        }
        alert.showAndWait();
        deleteId.setText("");
        BuildingPageContent.add();
        addDeleteBuildings((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Adds all buildings as content using a new Thread.
     * Doing this removes initial startup lag.
     *
     * @param stage Stage is passed as parameter to get all Nodes
     */
    private void addDeleteBuildings(Stage stage) {
        ThreadLock  lock = new ThreadLock();

        BuildingData data = new BuildingData(lock);
        Thread threadData = new Thread(data);
        threadData.setDaemon(true);
        threadData.start();

        BuildDeletePageContent deletePage = new BuildDeletePageContent(stage, lock);
        Thread threadDeleteBuildingPage = new Thread(deletePage);
        threadDeleteBuildingPage.setDaemon(true);
        threadDeleteBuildingPage.start();
    }

    /**
     * Adds rules to the textfields of the hours.
     * @param textField the textfield to add the rules to
     */
    private void addTextFieldListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                if (newValue.length() > 11) {
                    ((StringProperty) observable).setValue(oldValue);
                    return;
                }
                if (newValue.charAt(newValue.length() - 1) < 48 || newValue.charAt(newValue.length() - 1) > 57) {
                    if (newValue.charAt(newValue.length() - 1) != ':' && newValue.length() - 1 != 2 && newValue.length() - 1 != 8 && newValue.charAt(newValue.length() - 1) != '-' && newValue.length() - 1 != 5) {
                        String comp = "closed";
                        if (newValue.length() > comp.length() || newValue.charAt(newValue.length() - 1) != comp.charAt(newValue.length() - 1)) {
                            ((StringProperty) observable).setValue(oldValue);
                        }
                    }
                }
            }
        });
    }
}
