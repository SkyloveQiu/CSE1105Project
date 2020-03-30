package nl.tudelft.oopp.group43.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.RoomPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




public class RoomPageController {

    private boolean filterClicked = false;
    private String menu = "";

    @FXML
    private ChoiceBox<String> fromTime;
    @FXML
    private ChoiceBox<String> untilTime;
    @FXML
    private DatePicker date;
    @FXML
    private TextField searchBar;
    @FXML
    private AnchorPane timeDateSelect;
    @FXML
    private CheckBox blinds;
    @FXML
    private CheckBox desktop;
    @FXML
    private CheckBox projector;
    @FXML
    private CheckBox chalkBoard;
    @FXML
    private CheckBox microphone;
    @FXML
    private CheckBox smartBoard;
    @FXML
    private CheckBox whiteBoard;
    @FXML
    private CheckBox powerSupply;
    @FXML
    private CheckBox soundInstallation;
    @FXML
    private CheckBox wheelChair;
    @FXML
    private TextField space;
    @FXML
    private Label checkSpace;
    @FXML
    private GridPane roomList;

    @FXML
    private ScrollPane filterPanel;

    @FXML
    private Pane grayBackground;
    @FXML
    private GridPane editMenu;
    @FXML
    private GridPane addMenu;
    @FXML
    private Label editId;
    @FXML
    private Label addNumberCheck;
    @FXML
    private TextField addBuildingId;
    @FXML
    private TextField addRoomNumber;
    @FXML
    private TextField addRoomName;
    @FXML
    private Label addRoomNameCheck;
    @FXML
    private TextField addSpaceType;
    @FXML
    private TextField addChalkboard;
    @FXML
    private TextField addWhiteboard;
    @FXML
    private TextField addSmartboard;
    @FXML
    private TextField addBlinds;
    @FXML
    private TextField addDisplay;
    @FXML
    private TextField addDesktopPc;
    @FXML
    private TextField addProjector;
    @FXML
    private TextField addPowerSupply;
    @FXML
    private TextField addSurfaceArea;
    @FXML
    private TextField addSeatCapacity;
    @FXML
    private TextField addMicrophone;
    @FXML
    private TextField addSoundInstallation;
    @FXML
    private TextField addWheelchair;
    @FXML
    private TextField editRoomName;
    @FXML
    private TextField editSpaceType;
    @FXML
    private TextField editChalkboard;
    @FXML
    private TextField editWhiteboard;
    @FXML
    private TextField editSmartboard;
    @FXML
    private TextField editBlinds;
    @FXML
    private TextField editDisplay;
    @FXML
    private TextField editDesktopPc;
    @FXML
    private TextField editProjector;
    @FXML
    private TextField editPowerSupply;
    @FXML
    private TextField editSurfaceArea;
    @FXML
    private TextField editSeatCapacity;
    @FXML
    private TextField editMicrophone;
    @FXML
    private TextField editSoundInstallation;
    @FXML
    private TextField editWheelchair;
    @FXML
    private Label addSpaceTypeCheck;
    @FXML
    private Label addChalkboardCheck;
    @FXML
    private Label addWhiteboardCheck;
    @FXML
    private Label addSmartboardCheck;
    @FXML
    private Label addBlindsCheck;
    @FXML
    private Label addDisplayCheck;
    @FXML
    private Label addDesktopPcCheck;
    @FXML
    private Label addProjectorCheck;
    @FXML
    private Label addPowerSupplyCheck;
    @FXML
    private Label addSurfaceAreaCheck;
    @FXML
    private Label addSeatCapacityCheck;
    @FXML
    private Label addMicrophoneCheck;
    @FXML
    private Label addSoundInstallationCheck;
    @FXML
    private Label addWheelchairCheck;
    @FXML
    private Label editSpaceTypeCheck;
    @FXML
    private Label editChalkboardCheck;
    @FXML
    private Label editWhiteboardCheck;
    @FXML
    private Label editSmartboardCheck;
    @FXML
    private Label editBlindsCheck;
    @FXML
    private Label editDisplayCheck;
    @FXML
    private Label editDesktopPcCheck;
    @FXML
    private Label editProjectorCheck;
    @FXML
    private Label editPowerSupplyCheck;
    @FXML
    private Label editSurfaceAreaCheck;
    @FXML
    private Label editSeatCapacityCheck;
    @FXML
    private Label editMicrophoneCheck;
    @FXML
    private Label editSoundInstallationCheck;
    @FXML
    private Label editWheelchairCheck;
    @FXML
    private Label editRoomNameCheck;


    /**
     * Sets the hours in the choicebox to only the hours before until time choicebox.
     *
     * @param event Event passed by the box when clicked on
     */
    @FXML
    private void fromHourSelected(MouseEvent event) {
        String untilHour = untilTime.getValue();

        if (untilHour != null) {
            int i = 0;
            String time = "00:00";
            while (!untilHour.equals(time)) {
                i++;
                if (i < 10) {
                    time = "0" + i + ":00";
                } else {
                    time = i + ":00";
                }
            }

            ArrayList<String> hours = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (j < 10) {
                    hours.add("0" + j + ":00");
                } else {
                    hours.add(j + ":00");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            fromTime.setItems(list);

            RoomPageContent.setHoursFrom("");
        }
    }

    /**
     * Sets the hours in the choicebox to only the hours after the from time choicebox.
     *
     * @param event Event passed by the box when clicked on
     */
    @FXML
    private void untilHourSelected(MouseEvent event) {
        String fromHour = fromTime.getValue();

        if (fromHour != null) {
            int i = 0;
            String time = "00:00";
            while (!fromHour.substring(0, 2).equals(time.substring(0, 2))) {
                i++;
                if (i < 10) {
                    time = "0" + i + ":59";
                } else {
                    time = i + ":59";
                }
            }

            ArrayList<String> hours = new ArrayList<>();
            for (; i < 24; i++) {
                if (i < 10) {
                    hours.add("0" + i + ":59");
                } else {
                    hours.add(i + ":59");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            untilTime.setItems(list);

            RoomPageContent.setHoursTil("");
        }
    }

    /**
     * Searches for the rooms with the given query.
     *
     * @param event Event passed by the searchbutton when clicked on
     */
    @FXML
    private void searchRooms(MouseEvent event) {
        String searchQuery = searchBar.getText();
        if (searchQuery != null && searchQuery.length() > 0) {
            System.out.println("Searching in rooms for: " + searchQuery);

            JSONArray rooms = RoomPageContent.getDatabaseRooms();
            ArrayList<JSONObject> newRooms = new ArrayList<>();
            for (int i = 0; i < rooms.size(); i++) {
                if (StringChecker.containsIgnoreCase(searchQuery, ((String) ((JSONObject) rooms.get(i)).get("room_name"))) || StringChecker.containsIgnoreCase(searchQuery, ((String) ((JSONObject) ((JSONObject) rooms.get(i)).get("building")).get("building_name")))) {
                    System.out.println("room: " + ((JSONObject) rooms.get(i)).get("room_name") + " building: " + ((JSONObject) ((JSONObject) rooms.get(i)).get("building")).get("building_name") + " is correct");
                    newRooms.add((JSONObject) rooms.get(i));
                }
            }

            RoomPageContent.setSelectedRooms(newRooms);
            RoomPageContent.addRooms();
        } else {
            JSONArray rooms = RoomPageContent.getDatabaseRooms();
            ArrayList<JSONObject> selectedRooms = new ArrayList<>();
            for (int i = 0; i < rooms.size(); i++) {
                selectedRooms.add((JSONObject) rooms.get(i));
            }
            RoomPageContent.setSelectedRooms(selectedRooms);
            RoomPageContent.addRooms();
        }
    }

    @FXML
    private void showAddMenu() {
        grayBackground.setVisible(true);
        editMenu.setVisible(false);
        addMenu.setVisible(true);

    }

    @FXML
    private void closeAddMenu() {
        grayBackground.setVisible(false);
        addMenu.setVisible(false);
        RoomPageContent.setMenu(null);
    }

    /**
     * If you press the add building button, the method will try to do this operation, it it will be possible.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void addConfirm(ActionEvent event) {

        boolean ok = !addBuildingId.getText().isEmpty();
        ok = checkRoomName() && ok;
        ok = checkRoomNumber() && ok;
        ok = checkSpaceType() && ok;
        ok = checkChalkboard() && ok;
        ok = checkBlinds() && ok;
        ok = checkDesktop() && ok;
        ok = checkDisplay() && ok;
        ok = checkMicrophone() && ok;
        ok = checkPowerSupply() && ok;
        ok = checkProjector() && ok;
        ok = checkSmartbaord() && ok;
        ok = checkSoundInstallation() && ok;
        ok = checkWheelchair() && ok;
        ok = checkWhiteboard() && ok;
        ok = checkSeatCapacity() && ok;
        ok = checkSurfaceArea() && ok;

        if (ok) {
            System.out.println("dai balan");
            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(addBuildingId.getText()));

            JSONObject room = new JSONObject();
            room.put("room_name", addRoomName.getText());

            //room.put("id", Long.valueOf(addRoomNumber.getText()));
            room.put("building", building);

            JSONObject attributes = new JSONObject();
            attributes.put("blinds", Boolean.valueOf(addBlinds.getText()));
            attributes.put("display", Boolean.valueOf(addDisplay.getText()));
            attributes.put("desktopPc", Boolean.valueOf(addDesktopPc.getText()));
            attributes.put("projector", Boolean.valueOf(addProjector.getText()));
            attributes.put("spaceType", addSpaceType.getText());
            attributes.put("chalkBoard", Boolean.valueOf(addChalkboard.getText()));
            attributes.put("microphone", Boolean.valueOf(addMicrophone.getText()));
            attributes.put("smartBoard", Boolean.valueOf(addSmartboard.getText()));
            attributes.put("whiteBoard", Boolean.valueOf(addWhiteboard.getText()));
            attributes.put("powerSupply", Boolean.valueOf(addPowerSupply.getText()));
            attributes.put("surfaceArea", Long.valueOf(addSurfaceArea.getText()));
            attributes.put("seatCapacity", Long.valueOf(addSeatCapacity.getText()));
            attributes.put("soundInstallation", Boolean.valueOf(addSoundInstallation.getText()));
            attributes.put("wheelChairAccessible", Boolean.valueOf(addWheelchair.getText()));
            room.put("attributes", attributes.toJSONString());

            String message = ServerCommunication.sendRoom(room);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (message.equals("NOT OK")) {
                alert.setContentText("Something goes wrong during the procedure!" + "\n" + " Please try again!");

            } else {
                alert.setContentText("The operation has been successfully done!");
                RoomPageContent.reloadRooms();
            }

            alert.showAndWait();
            closeAddMenu();
        }
    }

    /**
     * Checks if the user put a number which is greater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     */
    private boolean checkBuildingNumber() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addBuildingId.getText().isEmpty()) {
                addNumberCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = addBuildingId.getText();
                long number = Long.valueOf(nunmberString);
                addNumberCheck.setText("");
                return true;
            } catch (Exception e) {
                addNumberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        } else {
            if (addBuildingId.getText().isEmpty()) {
                addNumberCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = addBuildingId.getText();
                long number = Long.valueOf(nunmberString);
                addNumberCheck.setText("");
                return true;
            } catch (Exception e) {
                addNumberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        }
    }

    private boolean checkRoomNumber() {

        if (addRoomNumber.getText().isEmpty()) {
            addNumberCheck.setText("You cannot have this field empty");
            return false;
        }
        try {
            String nunmberString = addRoomNumber.getText();
            long number = Long.valueOf(nunmberString);
            addNumberCheck.setText("");
            return true;
        } catch (Exception e) {
            addNumberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
            return false;
        }

    }

    @FXML
    private void checkRoomNumber(KeyEvent event) {
        checkRoomNumber();

    }


    /*
    =====================================================================
    METHODS FOR EDIT
     */

    @FXML
    private void showEditMenu(MouseEvent event) {
        grayBackground.setVisible(true);
        addMenu.setVisible(false);
        editMenu.setVisible(true);
    }

    @FXML
    private void closeEditMenu() {
        grayBackground.setVisible(false);
        editMenu.setVisible(false);
        RoomPageContent.setMenu(null);
    }


    /**
     * If you press the edit building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) {

        boolean ok = !editSpaceType.getText().isEmpty();
        ok = checkChalkboard() && ok;
        ok = checkBlinds() && ok;
        ok = checkDesktop() && ok;
        ok = checkDisplay() && ok;
        ok = checkMicrophone() && ok;
        ok = checkPowerSupply() && ok;
        ok = checkProjector() && ok;
        ok = checkSmartbaord() && ok;
        ok = checkSoundInstallation() && ok;
        ok = checkWheelchair() && ok;
        ok = checkWhiteboard() && ok;
        ok = checkSeatCapacity() && ok;
        ok = checkSurfaceArea() && ok;
        if (ok) {
            System.out.println(editId.getText());
            String[] array = editId.getText().split(";");
            JSONObject building = new JSONObject();
            building.put("building_number", Long.valueOf(array[1]));

            JSONObject room = new JSONObject();
            room.put("room_name", editRoomName.getText());

            room.put("id", Long.valueOf(array[0]));
            room.put("building", building);

            JSONObject attributes = new JSONObject();
            attributes.put("blinds", Boolean.valueOf(editBlinds.getText()));
            attributes.put("display", Boolean.valueOf(editDisplay.getText()));
            attributes.put("desktopPc", Boolean.valueOf(editDesktopPc.getText()));
            attributes.put("projector", Boolean.valueOf(editProjector.getText()));
            attributes.put("spaceType", editSpaceType.getText());
            attributes.put("chalkBoard", Boolean.valueOf(editChalkboard.getText()));
            attributes.put("microphone", Boolean.valueOf(editMicrophone.getText()));
            attributes.put("smartBoard", Boolean.valueOf(editSmartboard.getText()));
            attributes.put("whiteBoard", Boolean.valueOf(editWhiteboard.getText()));
            attributes.put("powerSupply", Boolean.valueOf(editPowerSupply.getText()));
            attributes.put("surfaceArea", Long.valueOf(editSurfaceArea.getText()));
            attributes.put("seatCapacity", Long.valueOf(editSeatCapacity.getText()));
            attributes.put("soundInstallation", Boolean.valueOf(editSoundInstallation.getText()));
            attributes.put("wheelChairAccessible", Boolean.valueOf(editWheelchair.getText()));
            room.put("attributes", attributes.toJSONString());

            String message = ServerCommunication.sendRoom(room);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (message.equals("NOT OK")) {
                alert.setContentText("Something goes wrong during the procedure!" + "\n" + " Please try again!");

            } else {
                alert.setContentText("The operation has been successfully done!");
                RoomPageContent.reloadRooms();
            }

            alert.showAndWait();

            closeEditMenu();


        }

    }

    /*
    =====================================================================
    END OF CRUD METHODS
     */

    /**
     * Drops down or retracts the filters when clicked on the filter button.
     *
     * @param event event passed when clicked on the button.
     */
    @FXML
    private void dropFilter(MouseEvent event) {
        filterClicked = !filterClicked;
        filterPanel.setVisible(filterClicked);
    }

    /**
     * If you press the confirm filtering button, the method will give the chosen attributes to the server and it will show the list of rooms.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void confirmFilters(ActionEvent event) {
        String blindsString = "false";
        String desktopString = "false";
        String projectorString = "false";
        String chalkBoardString = "false";
        String microphoneString = "false";
        String smartBoardString = "false";
        String whiteBoardString = "false";
        String powerSupplyString = "false";
        String soundInstallationString = "false";
        String wheelChairString = "false";

        if (blinds.isSelected()) {
            blindsString = "true";
        }
        if (desktop.isSelected()) {
            desktopString = "true";
        }
        if (projector.isSelected()) {
            projectorString = "true";
        }
        if (chalkBoard.isSelected()) {
            chalkBoardString = "true";
        }
        if (microphone.isSelected()) {
            microphoneString = "true";
        }
        if (smartBoard.isSelected()) {
            smartBoardString = "true";
        }
        if (whiteBoard.isSelected()) {
            whiteBoardString = "true";
        }
        if (powerSupply.isSelected()) {
            powerSupplyString = "true";
        }
        if (soundInstallation.isSelected()) {
            soundInstallationString = "true";
        }
        if (wheelChair.isSelected()) {
            wheelChairString = "true";
        }


        if (checkNumber() == true) {
            getRoomsFilter(blindsString, desktopString, projectorString, chalkBoardString, microphoneString, smartBoardString, whiteBoardString, powerSupplyString,
                    soundInstallationString, wheelChairString, space.getText());
        }


    }

    /**
     * Takes the rooms from the server with the chosen attributes.
     */
    public void getRoomsFilter(String blinds, String desktop, String projector, String chalkBoard, String microphone, String smartBoard, String whiteBoard, String powerSupply, String soundInstallation, String wheelChair, String space) {
        JSONParser json = new JSONParser();

        try {
            String response = ServerCommunication.getRoomFilter(blinds, desktop, projector, chalkBoard, microphone, smartBoard, whiteBoard, powerSupply, soundInstallation, wheelChair, space);

            Label load = new Label("Loading Rooms");
            roomList.getChildren().add(0, load);

            /*
            Checks if the rooms are in JSONArray format or in JSONObject format.
            If it is something other than this it means there was an error with the server communication.
            If that happens it shows an 'useful' error :).
             */
            if (response.charAt(0) == '[') {
                JSONArray rooms = (JSONArray) json.parse(response);
                ArrayList<JSONObject> filterSelection = new ArrayList<>();

                for (int i = 0; i < rooms.size(); i++) {
                    filterSelection.add((JSONObject) rooms.get(i));
                }
                RoomPageContent.setSelectedRooms(filterSelection);
                load.setText("");
                RoomPageContent.addRooms();
            } else {
                load.setText("Oops, something went wrong,\nplease check your internet connection and try again");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the user put a number which is grater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     *
     * @return true if the field contains a number which is strictly greater than -1, otherwise false.
     */
    @FXML
    private boolean checkNumber() {
        if (space.getText().isEmpty()) {
            space.setText("0");
            return true;
        }
        String text = "You must put a number which " + "\n" + "is greater than -1";
        try {
            String nunmberString = space.getText();
            System.out.println(Long.MAX_VALUE);
            long number = Long.valueOf(nunmberString);
            if (number < 0) {
                checkSpace.setText(text);
                return false;
            } else {
                checkSpace.setText("");
                return true;
            }
        } catch (Exception e) {
            checkSpace.setText(text);
            return false;
        }
    }

    /**
     * When the confirm button for the time gets pressed the hours and date get parsed into the correct format.
     *
     * @param event Event passed by the box when clicked on
     */
    @FXML
    private void confirmDateTime(ActionEvent event) {
        if (!RoomPageContent.getHoursFrom().equals("") && !RoomPageContent.getHoursTil().equals("") && RoomPageContent.getDate() != null) {
            ReservationConfig.resetSelectedHours();

            LocalDate localDate = date.getValue();

            String month = Integer.toString(localDate.getMonthValue());
            String day = Integer.toString(localDate.getDayOfMonth());
            if (localDate.getMonthValue() < 10) {
                month = "0" + localDate.getMonthValue();
            }
            if (localDate.getDayOfMonth() < 10) {
                day = "0" + localDate.getDayOfMonth();
            }
            String dateString = localDate.getYear() + "-" + month + "-" + day;
            int hoursBetween = Integer.parseInt(untilTime.getValue().substring(0, 2)) - Integer.parseInt(fromTime.getValue().substring(0, 2));

            for (int i = 0; i <= hoursBetween; i++) {
                int offset = i + Integer.parseInt(fromTime.getValue().substring(0, 2));
                String hourOffset = "";
                if (offset < 10) {
                    hourOffset = "-0" + offset;
                } else {
                    hourOffset = "-" + offset;
                }
                String hour = dateString + hourOffset;
                ReservationConfig.addHour(hour);
            }


            /*
            ===========================================
             */


            //ArrayList selectedHours = ReservationConfig.getSelectedHours();
            //
            //String response = "";
            //
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
            //formatter.withZone(ZoneId.of("UTC"));
            //for (int i = 0; i < selectedHours.size(); i++) {
            //    LocalDateTime startDate = LocalDateTime.parse((String) selectedHours.get(i), formatter);
            //    LocalDateTime endDate = startDate.plusHours(1);
            //
            //    String startTime = startDate.toString() + ":00.000+0000";
            //    String endTime = endDate.toString() + ":00.000+0000";
            //    System.out.println(startTime);
            //    response = ServerCommunication.reserveRoomForHour(startTime, endTime);
            //
            //    System.out.println(response);
            //}


            /*
            ==========================================
             */
            timeDateSelect.setVisible(false);
            RoomPageContent.addRooms();
        }
    }

    /**
     * Checks if the Chalkboard field is well completed (for both edit and add menu).
     *
     * @return true if the Chalkboard field is well completed, false otherwise.
     */
    private boolean checkChalkboard() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addChalkboard.getText().isEmpty() || (!addChalkboard.getText().equals("true") && !addChalkboard.getText().equals("false"))) {
                addChalkboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addChalkboardCheck.setText("");
            return true;
        } else {
            if (editChalkboard.getText().isEmpty() || (!editChalkboard.getText().equals("true") && !editChalkboard.getText().equals("false"))) {
                editChalkboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editChalkboardCheck.setText("");
            return true;
        }

    }


    /**
     * Checks if the Chalkboard field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkChalkboard(KeyEvent event) {
        checkChalkboard();
    }

    /**
     * Checks if the Whiteboard field is well completed (for both edit and add menu).
     *
     * @return true if the Whiteboard field is well completed, false otherwise.
     */
    private boolean checkWhiteboard() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addWhiteboard.getText().isEmpty() || (!addWhiteboard.getText().equals("true") && !addWhiteboard.getText().equals("false"))) {
                addWhiteboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addWhiteboardCheck.setText("");
            return true;
        } else {
            if (editWhiteboard.getText().isEmpty() || (!editWhiteboard.getText().equals("true") && !editWhiteboard.getText().equals("false"))) {
                editWhiteboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editWhiteboardCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Whiteboard field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkWhiteboard(KeyEvent event) {
        checkWhiteboard();
    }

    /**
     * Checks if the Smartboard field is well completed (for both edit and add menu).
     *
     * @return true if the Smartboard field is well completed, false otherwise.
     */
    private boolean checkSmartbaord() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addSmartboard.getText().isEmpty() || (!addSmartboard.getText().equals("true") && !addSmartboard.getText().equals("false"))) {
                addSmartboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addSmartboardCheck.setText("");
            return true;
        } else {
            if (editSmartboard.getText().isEmpty() || (!editSmartboard.getText().equals("true") && !editSmartboard.getText().equals("false"))) {
                editSmartboardCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editSmartboardCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Smartboard field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkSmartboard(KeyEvent event) {
        checkSmartbaord();
    }


    /**
     * Checks if the Blinds field is well completed (for both edit and add menu).
     *
     * @return true if the Blinds field is well completed, false otherwise.
     */
    private boolean checkBlinds() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addBlinds.getText().isEmpty() || (!addBlinds.getText().equals("true") && !addBlinds.getText().equals("false"))) {
                addBlindsCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addBlindsCheck.setText("");
            return true;
        } else {
            if (editBlinds.getText().isEmpty() || (!editBlinds.getText().equals("true") && !editBlinds.getText().equals("false"))) {
                editBlindsCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editBlindsCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Blinds field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkBlinds(KeyEvent event) {
        checkBlinds();
    }

    /**
     * Checks if the Display field is well completed (for both edit and add menu).
     *
     * @return true if the Display field is well completed, false otherwise.
     */
    private boolean checkDisplay() {
        if (RoomPageContent.getMenu().equals("add")) {

            if (addDisplay.getText().isEmpty() || (!addDisplay.getText().equals("true") && !addDisplay.getText().equals("false"))) {
                addDisplayCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addDisplayCheck.setText("");
            return true;
        } else {
            if (editDisplay.getText().isEmpty() || (!editDisplay.getText().equals("true") && !editDisplay.getText().equals("false"))) {
                editDisplayCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editDisplayCheck.setText("");
            return true;
        }


    }


    /**
     * Checks if the Display field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkDisplay(KeyEvent event) {
        checkDisplay();
    }


    /**
     * Checks if the Desktop field is well completed (for both edit and add menu).
     *
     * @return true if the Desktop field is well completed, false otherwise.
     */
    private boolean checkDesktop() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addDesktopPc.getText().isEmpty() || (!addDesktopPc.getText().equals("true") && !addDesktopPc.getText().equals("false"))) {
                addDesktopPcCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addDesktopPcCheck.setText("");
            return true;
        } else {
            if (editDesktopPc.getText().isEmpty() || (!editDesktopPc.getText().equals("true") && !editDesktopPc.getText().equals("false"))) {
                editDesktopPcCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editDesktopPcCheck.setText("");
            return true;
        }

    }


    /**
     * Checks if the Desktop field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkDesktop(KeyEvent event) {
        checkDesktop();
    }

    /**
     * Checks if the Projector field is well completed (for both edit and add menu).
     *
     * @return true if the Projector field is well completed, false otherwise.
     */
    private boolean checkProjector() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addProjector.getText().isEmpty() || (!addProjector.getText().equals("true") && !addProjector.getText().equals("false"))) {
                addProjectorCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addProjectorCheck.setText("");
            return true;
        } else {
            if (editProjector.getText().isEmpty() || (!editProjector.getText().equals("true") && !editProjector.getText().equals("false"))) {
                editProjectorCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editProjectorCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Projector field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkProjector(KeyEvent event) {
        checkProjector();
    }

    /**
     * Checks if the PowerSupply field is well completed (for both edit and add menu).
     *
     * @return true if the PowerSupply field is well completed, false otherwise.
     */
    private boolean checkPowerSupply() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addPowerSupply.getText().isEmpty() || (!addPowerSupply.getText().equals("true") && !addPowerSupply.getText().equals("false"))) {
                addPowerSupplyCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addPowerSupplyCheck.setText("");
            return true;
        } else {
            if (editPowerSupply.getText().isEmpty() || (!editPowerSupply.getText().equals("true") && !editPowerSupply.getText().equals("false"))) {
                editPowerSupplyCheck.setText("You have to complete this field with 'true' or 'false' !");
                return false;
            }
            editPowerSupplyCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the PowerSupply field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkPowerSupply(KeyEvent event) {
        checkPowerSupply();
    }

    /**
     * Checks if the SurfaceArea field is well completed (for both edit and add menu).
     *
     * @return true if the SurfaceArea field is well completed, false otherwise.
     */
    private boolean checkSurfaceArea() {


        if (RoomPageContent.getMenu().equals("add")) {
            if (addSurfaceArea.getText().isEmpty()) {
                //addNumberCheck.setText("You cannot have this field empty");
                addSurfaceAreaCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = addSurfaceArea.getText();
                long number = Long.valueOf(nunmberString);
                addSurfaceAreaCheck.setText("");
                return true;
            } catch (Exception e) {
                //addSurfaceArea.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                addSurfaceAreaCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        } else {
            if (editSurfaceArea.getText().isEmpty()) {
                //addNumberCheck.setText("You cannot have this field empty");
                editSurfaceAreaCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = editSurfaceArea.getText();
                long number = Long.valueOf(nunmberString);
                editSurfaceAreaCheck.setText("");
                return true;
            } catch (Exception e) {
                //addSurfaceArea.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                editSurfaceAreaCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        }


    }

    /**
     * Checks if the SurfaceArea field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkSurfaceArea(KeyEvent event) {
        checkSurfaceArea();
    }

    /**
     * Checks if the SeatCapacity field is well completed (for both edit and add menu).
     *
     * @return true if the SeatCapacity field is well completed, false otherwise.
     */
    private boolean checkSeatCapacity() {

        if (RoomPageContent.getMenu().equals("add")) {

            if (addSeatCapacity.getText().isEmpty()) {
                //addNumberCheck.setText("You cannot have this field empty");
                addSeatCapacityCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = addSeatCapacity.getText();
                long number = Long.valueOf(nunmberString);
                addSeatCapacityCheck.setText("");
                return true;
            } catch (Exception e) {
                // addSurfaceArea.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                addSeatCapacityCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        } else {
            if (editSeatCapacity.getText().isEmpty()) {
                //addNumberCheck.setText("You cannot have this field empty");
                editSeatCapacityCheck.setText("You cannot have this field empty");
                return false;
            }
            try {
                String nunmberString = editSeatCapacity.getText();
                long number = Long.valueOf(nunmberString);
                editSeatCapacityCheck.setText("");
                return true;
            } catch (Exception e) {
                // addSurfaceArea.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                editSeatCapacityCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
                return false;
            }
        }

    }


    /**
     * Checks if the SeatCapacity field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkSeatCapacity(KeyEvent event) {
        checkSeatCapacity();
    }

    /**
     * Checks if the Microphone field is well completed (for both edit and add menu).
     *
     * @return true if the Microphone field is well completed, false otherwise.
     */
    private boolean checkMicrophone() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addMicrophone.getText().isEmpty() || (!addMicrophone.getText().equals("true") && !addMicrophone.getText().equals("false"))) {
                addMicrophoneCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addMicrophoneCheck.setText("");
            return true;

        } else {
            if (editMicrophone.getText().isEmpty() || (!editMicrophone.getText().equals("true") && !editMicrophone.getText().equals("false"))) {
                editMicrophoneCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editMicrophoneCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Microphone field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkMicrophone(KeyEvent event) {
        checkMicrophone();
    }

    /**
     * Checks if the SoundInstallation field is well completed (for both edit and add menu).
     *
     * @return true if the SoundInstallation field is well completed, false otherwise.
     */
    private boolean checkSoundInstallation() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addSoundInstallation.getText().isEmpty() || (!addSoundInstallation.getText().equals("true") && !addSoundInstallation.getText().equals("false"))) {
                addSoundInstallationCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addSoundInstallationCheck.setText("");
            return true;
        } else {
            if (editSoundInstallation.getText().isEmpty() || (!editSoundInstallation.getText().equals("true") && !editSoundInstallation.getText().equals("false"))) {
                editSoundInstallationCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editSoundInstallationCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the SoundInstallation field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkSoundInstallation(KeyEvent event) {
        checkSoundInstallation();
    }

    /**
     * Checks if the Wheelchair field is well completed (for both edit and add menu).
     *
     * @return true if the Wheelchair field is well completed, false otherwise.
     */
    private boolean checkWheelchair() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addWheelchair.getText().isEmpty() || (!addWheelchair.getText().equals("true") && !addWheelchair.getText().equals("false"))) {
                addWheelchairCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            addWheelchairCheck.setText("");
            return true;
        } else {
            if (editWheelchair.getText().isEmpty() || (!editWheelchair.getText().equals("true") && !editWheelchair.getText().equals("false"))) {
                editWheelchairCheck.setText("You have to complete this field with 'true'  or 'false' !");
                return false;
            }
            editWheelchairCheck.setText("");
            return true;
        }

    }

    /**
     * Checks if the Wheelchair field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkWheelchair(KeyEvent event) {
        checkWheelchair();
    }

    /**
     * Checks if the SpaceType field is well completed (for both edit and add menu).
     *
     * @return true if the SpaceType field is well completed, false otherwise.
     */
    private boolean checkSpaceType() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addSpaceType.getText().isEmpty()) {
                addSpaceTypeCheck.setText("This field must not be empty");
                return false;
            } else {
                addSpaceTypeCheck.setText("");
                return true;
            }
        } else {
            if (editSpaceType.getText().isEmpty()) {
                editSpaceTypeCheck.setText("This field must not be empty");
                return false;
            } else {
                editSpaceTypeCheck.setText("");
                return true;
            }

        }
    }

    /**
     * Checks if the SpaceType field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkSpaceType(KeyEvent event) {
        checkSpaceType();
    }

    /**
     * Checks if the RoomName field is well completed (for both edit and add menu).
     *
     * @return true if the RoomName field is well completed, false otherwise.
     */
    private boolean checkRoomName() {
        if (RoomPageContent.getMenu().equals("add")) {
            if (addRoomName.getText().isEmpty()) {
                addRoomNameCheck.setText("You cannot have this field empty");
                return false;
            } else {
                addRoomNameCheck.setText("");
                return true;

            }
        } else {
            if (editRoomName.getText().isEmpty()) {
                editRoomNameCheck.setText("You cannot have this field empty");
                return false;
            } else {
                editRoomNameCheck.setText("");
                return true;

            }
        }
    }

    /**
     * Checks if the RoomName field is well completed (for both edit and add menu).
     *
     * @param event - the user introduce some input in the field.
     */
    @FXML
    private void checkRoomName(KeyEvent event) {
        checkRoomName();
    }


}
