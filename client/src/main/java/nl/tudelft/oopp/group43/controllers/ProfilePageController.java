package nl.tudelft.oopp.group43.controllers;

import java.io.IOException;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.ProfilePageContent;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;


public class ProfilePageController {

    @FXML
    private AnchorPane changePasswordMenu;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Label oldPasswordCheck;
    @FXML
    private Label newPasswordCheck;
    @FXML
    private Label confirmPasswordCheck;



    @FXML
    private void showMyReservationsMenu(ActionEvent event) {
    }

    @FXML
    private void showChangePasswordMenu(ActionEvent event) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), changePasswordMenu);
        oldPassword.setText("");
        oldPasswordCheck.setText("");
        newPassword.setText("");
        newPasswordCheck.setText("");
        confirmPassword.setText("");
        confirmPasswordCheck.setText("");
        st.setFromX(0);
        st.setToX(1);
        st.setFromY(0);
        st.setToY(1);
        st.setCycleCount(1);

        st.play();

        changePasswordMenu.setVisible(true);
    }

    @FXML
    private void logOut(ActionEvent event) {
        ServerCommunication.setToken("invalid");
        SceneLoader.setScene("main");
        SceneLoader sl = new SceneLoader();
        try {
            sl.start((Stage) ((Node) event.getSource()).getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeChangePasswordMenu(ActionEvent actionEvent) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), changePasswordMenu);
        st.setFromX(1);
        st.setToX(0);
        st.setFromY(1);
        st.setToY(0);
        st.setCycleCount(1);

        st.setOnFinished(e -> changePasswordMenu.setVisible(false));
        st.play();
    }

    /**
     * Checks if the Old Password field was completed with good input.
     * @return true if the Old Password field was completed with good input, false otherwise.
     */
    private boolean checkOldPassword()
    {
        if(oldPassword.getText().isEmpty())
        {
            oldPasswordCheck.setText("This field is empty! Please introduce your old password");
            return false;
        }

        if(oldPassword.getText().length() < 8)
        {
            System.out.println("Your old password should have at least 8 characters");
            oldPasswordCheck.setText("Your old password should have at least 8 characters");
            return false;
        }
        oldPasswordCheck.setText("");
        return true;
    }
    /**
     * Checks if the New Password field was completed with good input.
     * @return true if the New Password field was completed with good input, false otherwise.
     */
    private boolean checkNewPassword()
    {
        if(newPassword.getText().isEmpty())
        {
            newPasswordCheck.setText("This field is empty! Please introduce your old password");
            return false;
        }
        if(newPassword.getText().length() < 8)
        {
            System.out.println("Your old password should have at least 8 characters");
            newPasswordCheck.setText("Your old password should have at least 8 characters");
            return false;
        }
        newPasswordCheck.setText("");
        return true;
    }

    /**
     * Checks if the Confirm Password field was completed with good input.
     * @return true if the Confirm Password field was completed with good input, false otherwise.
     */
    private boolean checkConfirmPassword()
    {
        if(confirmPassword.getText().isEmpty())
        {
            confirmPasswordCheck.setText("This field is empty! Please introduce your old password");
            return false;
        }
        if(confirmPassword.getText().length() < 8)
        {
            System.out.println("Your old password should have at least 8 characters");
            confirmPasswordCheck.setText("Your old password should have at least 8 characters");
            return false;
        }
        if(confirmPassword.getText().equals(newPassword.getText()) == false)
        {
            confirmPasswordCheck.setText("The newer password does not match!");
            return false;
        }
        confirmPasswordCheck.setText("");
        return true;
    }

    /**
     * Checks if the Old Password field was completed with good input every time the user introduce a new character.
     * @param keyEvent - user introduce a new character
     */
    @FXML
    public void checkOldPassword(KeyEvent keyEvent) {
        checkOldPassword();
    }

    /**
     * Checks if the New Password field was completed with good input every time the user introduce a new character.
     * @param keyEvent - user introduce a new character
     */
    @FXML
    public void checkNewPassword(KeyEvent keyEvent) {
        checkNewPassword();
    }

    /**
     * Checks if the Confirm Password field was completed with good input every time the user introduce a new character.
     * @param keyEvent - user introduce a new character
     */
    @FXML
    public void checkConfimrPassword(KeyEvent keyEvent) {
        checkConfirmPassword();
    }

    /**
     * When user pressed on the Button, it checks if all fields were completed using good input and does the communication with the server.
     * @param event - the button is clicked.
     */
    @FXML
    private void changePasswordConfirm(ActionEvent event)
    {
        boolean ok = checkConfirmPassword();
        ok = checkNewPassword() && ok;
        ok = checkOldPassword() && ok;
        if(ok == true)
        {
            System.out.println("Poti sa apesi");
        }
    }


}
