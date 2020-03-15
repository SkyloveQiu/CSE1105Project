package nl.tudelft.oopp.group43.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.FilterContent;
import nl.tudelft.oopp.group43.communication.ServerCommunication;


public class FilteringSceneController {
    @FXML
    CheckBox blinds;
    @FXML
    CheckBox desktop;
    @FXML
    CheckBox projector;
    @FXML
    CheckBox chalkBoard;
    @FXML
    CheckBox microphone;
    @FXML
    CheckBox smartBoard;
    @FXML
    CheckBox whiteBoard;
    @FXML
    CheckBox powerSupply;
    @FXML
    CheckBox soundInstallation;
    @FXML
    CheckBox wheelChair;
    @FXML
    TextField space;
    @FXML
    Label checkSpace;

    /**
     * If you press the confirm filtering button, the method will give the chosen attributes to the server and it will show the list of rooms.
     *
     * @param event - pressing the button
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void confirmClicked(ActionEvent event) {
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


            FilterContent filterContent = new FilterContent((Stage) ((Node) event.getSource()).getScene().getWindow());
            filterContent.getRoomsFilter(blindsString, desktopString, projectorString, chalkBoardString, microphoneString, smartBoardString, whiteBoardString, powerSupplyString,
                    soundInstallationString, wheelChairString, space.getText());
        }


    }

    /**
     * Checks if the user put a number which is grater than 0 and smaller than long.MAX_VALUE and show a proper message to the user.
     * @return true if the field contains a number which is strictly greater than -1, otherwise false.
     */
    @FXML
    @SuppressWarnings("unchecked")
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


}
