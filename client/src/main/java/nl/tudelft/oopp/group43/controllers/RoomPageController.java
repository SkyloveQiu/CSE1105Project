package nl.tudelft.oopp.group43.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.BuildDataScene;
import nl.tudelft.oopp.group43.classes.BuildingData;
import nl.tudelft.oopp.group43.classes.BuildingEditPageContent;
import nl.tudelft.oopp.group43.classes.ReservationConfig;
import nl.tudelft.oopp.group43.classes.StringChecker;
import nl.tudelft.oopp.group43.classes.ThreadLock;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import nl.tudelft.oopp.group43.content.RoomPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RoomPageController {

    private boolean filterClicked = false;

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
    private Label addBuildingNumber;
    @FXML
    private Label addNumberCheck;

    /**
     * Sets the hours in the choicebox to only the hours before until time choicebox.
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
                    hours.add("0" + Integer.toString(j) + ":00");
                } else {
                    hours.add(Integer.toString(j) + ":00");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            fromTime.setItems(list);

            RoomPageContent.setHoursFrom("");
        }
    }

    /**
     * Sets the hours in the choicebox to only the hours after the from time choicebox.
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
                    hours.add("0" + Integer.toString(i) + ":59");
                } else {
                    hours.add(Integer.toString(i) + ":59");
                }
            }
            ObservableList<String> list = FXCollections.observableArrayList(hours);
            untilTime.setItems(list);

            RoomPageContent.setHoursTil("");
        }
    }

    /**
     * Searches for the rooms with the given query.
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
                    System.out.println("room: " + (String) ((JSONObject) rooms.get(i)).get("room_name") + " building: " + (String) ((JSONObject) ((JSONObject) rooms.get(i)).get("building")).get("building_name") + " is correct");
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
    }

    /**
     * If you press the add building button, the method will try to do this operation, it it will be possible.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void addConfirm(ActionEvent event) {

        closeAddMenu();
    }

    /**
     * Checks if the user put a number which is greater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
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
            long number = Long.valueOf(nunmberString);
            addNumberCheck.setText("");
            return true;
        } catch (Exception e) {
            addNumberCheck.setText("You must put a number which is greater than 0 and less than " + Long.MAX_VALUE);
            return false;
        }
    }

    /**
     * Checks if the Building Name field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean addCheckName() {
        return true;
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
    }


    /**
     * If you press the edit building button, the method will check if you selected a building and do the delete operation.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editConfirm(ActionEvent event) {

        closeEditMenu();
    }

    /**
     * Checks if the Building Name field is not empty.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void editCheckName() {

    }

    /*
    =====================================================================
    END OF CRUD METHODS
     */

    /**
     * Drops down or retracts the filters when clicked on the filter button.
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

}
