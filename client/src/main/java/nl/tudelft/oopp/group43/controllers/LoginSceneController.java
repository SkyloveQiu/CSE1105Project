package nl.tudelft.oopp.group43.controllers;

import static nl.tudelft.oopp.group43.controllers.RegisterSceneController.emailValid;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.views.MainPageDisplay;
import nl.tudelft.oopp.group43.views.RegisterDisplay;

import org.json.simple.parser.ParseException;

public class LoginSceneController {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label emailCheck;
    @FXML
    private Label passwordCheck;


    /**
     * Checks if all fields are complete and valid.
     * @return true if all fields are complete and valid, false otherwise
     */
    boolean checkEmpty() {

        boolean okEmpty = emptyEmail();
        okEmpty = (emptyPassword() && okEmpty);
        return okEmpty;
    }


    /**
     * If you press the login button, either you can be redirected to the main page if all fields are valid,
     * or you have to change something in your fields.
     * @param event - login button is pressed
     * @throws IOException    - if loading the Register Page fails
     * @throws ParseException - if something goes wrong with the JSON Parser from the loginToken method.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void loginClicked(ActionEvent event) throws IOException, ParseException {
        boolean okEmpty = checkEmpty();
        if (okEmpty == false) {
            return;
        }

        String response = ServerCommunication.loginToken(email.getText(), password.getText());

        if (response.equals("OK")) {
            emailCheck.setText("");
            ServerCommunication.setUsername(email.getText());
            MainPageDisplay md = new MainPageDisplay();
            md.start((Stage) ((Node) event.getSource()).getScene().getWindow());
        } else {
            emailCheck.setText("Wrong passsword or email");
        }
    }


    /**
     * Checks if the email field is empty and show special messages to the user.
     * @return true if the email field is not empty and valid, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean emptyEmail() {
        email.getText();
        if (email.getText().isEmpty()) {
            emailCheck.setText("You did not complete the email field");
            return false;
        } else {
            if (emailValid(email.getText()) == false) {
                emailCheck.setText("The email is not valid");
                return false;
            } else {
                emailCheck.setText("");
                return true;
            }
        }

    }


    /**
     * Checks if the password field is empty and show special messages to the user.
     * @return true if the password field is not empty and valid, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean emptyPassword() {
        String passwordString = password.getText();
        if (passwordString.isEmpty()) {
            passwordCheck.setText("You did not complete the password field");
            return false;
        } else {
            passwordCheck.setText("");
            return true;
        }
    }


    /**
     * If you press the register button, you will be redirected to the Register Page.
     * @param event - register button is pressed
     * @throws IOException - if loading the Register Page fails
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void registerClicked(ActionEvent event) throws IOException {
        RegisterDisplay rd = new RegisterDisplay();
        rd.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

}
