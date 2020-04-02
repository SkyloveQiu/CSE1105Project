package nl.tudelft.oopp.group43.controllers;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.components.BackButton;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

public class RegisterPageController {

    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")"
            + "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField cpassword;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private CheckBox check;
    @FXML
    private RadioButton employee;
    @FXML
    private RadioButton student;
    @FXML
    private RadioButton other;
    @FXML
    private Label emailCheck;
    @FXML
    private Label passwordCheck;
    @FXML
    private Label cpasswordCheck;
    @FXML
    private Label termsCheck;
    @FXML
    private Label roleCheck;
    @FXML
    private Label firstNameCheck;
    @FXML
    private Label lastNameCheck;

    /**
     * Checks if the email is valid.
     *
     * @param email - the string which represents the email
     * @return true if the email is valid, false otherwise
     */
    public static boolean emailValid(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = emailPattern.matcher(email.toLowerCase());
        return matcher.matches();
    }


    /**
     * Checks if all fields are complete and valid.
     *
     * @return true if all fields are complete and valid, false otherwise
     */
    private boolean checkEmpty() {

        boolean empty = checkEmail();
        empty = (checkFirstName() && empty);
        empty = (checkLastName() && empty);
        empty = (checkPassword() && empty);
        empty = (checkCPassword() && empty);
        empty = (checkRole() && empty);
        empty = (checkBoxCheck() && empty);

        return empty;
    }

    /**
     * If you press the confirm button, either you can be redirected to the login page if all fields are valid,
     * or you have to change something in your fields.
     *
     * @param event - pressing the button
     * @throws IOException - if loading the Login Display fails
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void confirmClicked(ActionEvent event) throws IOException {

        if (checkEmpty() == false) {
            return;
        }

        String role;
        if (employee.isSelected()) {
            role = "employee";
        } else if (student.isSelected()) {
            role = "student";
        } else {
            role = "other";
        }

        String response = ServerCommunication.confirmRegistration(firstName.getText(), lastName.getText(), email.getText(), password.getText(), role);
        if (response.equals("OK")) {
            emailCheck.setText("");

            SceneLoader.setScene("login");
            SceneLoader sl = new SceneLoader();
            sl.start((Stage) ((Node) event.getSource()).getScene().getWindow());
        } else {
            emailCheck.setText("The email already exists!");
        }

    }

    /**
     * Checks if the input from the user is good for the First Name field.
     * @param event - the user puts a new character in the First Name field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkFirstName(KeyEvent event) {
        checkFirstName();
    }


    /**
     * Checks if the first name field is empty and show special messages to the user.
     *
     * @return true if the first name field is not empty, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkFirstName() {
        if (firstName.getText().isEmpty()) {
            firstNameCheck.setText("You did not complete the first name field");
            return false;
        } else {
            firstNameCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the input from the user is good for the Last Name field.
     * @param event - the user puts a new character in the Last Name field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkLastName(KeyEvent event) {
        checkLastName();
    }


    /**
     * Checks if the last name field is empty and show special messages to the user.
     *
     * @return true if the last name field is not empty, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkLastName() {
        if (lastName.getText().isEmpty()) {
            lastNameCheck.setText("You did not complete the last name field");
            return false;
        } else {
            lastNameCheck.setText("");
            return true;
        }
    }

    /**
     * Checks if the email field is empty and show special messages to the user.
     *
     * @return true if the email field is not empty and valid, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkEmail() {
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
     * Checks if the input from the user is good for the Password field.
     * @param event - the user puts a new character in the Password field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkPassword(KeyEvent event) {
        checkPassword();
    }


    /**
     * Checks if the password field is empty and show special messages to the user.
     * + a password must be between 8 and 32 characters
     *
     * @return true if the password field is not empty and valid, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkPassword() {
        boolean ok = true;
        if (password.getText().isEmpty()) {
            passwordCheck.setText("You did not complete the password field");
            return false;
        } else {
            if (password.getText().length() < 8 || password.getText().length() > 32) {
                if (password.getText().length() < 8) {
                    passwordCheck.setText("The password must have at least 8 characters");
                } else {
                    passwordCheck.setText("The password must have maximum 32 characters");
                }
                ok = false;
            } else {
                passwordCheck.setText("");
            }
            if (cpassword.getText().isEmpty() || !password.getText().equals(cpassword.getText())) {
                cpasswordCheck.setText("The passwords don't match");
                return false;
            } else {
                return ok;
            }
        }
    }

    /**
     * Checks if the input from the user is good for the Confirm Password field.
     * @param event - the user puts a new character in the Confirm Password field.
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkCPassword(KeyEvent event) {
        checkCPassword();
    }


    /**
     * Checks if the confirm password field is empty and show special messages to the user.
     *
     * @return true if the confirm password field is not empty and valid, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkCPassword() {
        if (cpassword.getText().isEmpty()) {
            cpasswordCheck.setText("You did not complete the confirm password field");
            return false;
        } else {
            if (password.getText().isEmpty() || !password.getText().equals(cpassword.getText())) {
                cpasswordCheck.setText("The passwords don't match");
                return false;
            } else {
                cpasswordCheck.setText("");
                return true;
            }
        }
    }

    /**
     * This method was created to show messages every time the user select or deselect the check box.
     *
     * @param event - the check box is selected or not
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void checkBoxCheck(ActionEvent event) {
        checkBoxCheck();
    }

    /**
     * Checks if the terms field is selected and show special messages to the user.
     *
     * @return true if the terms field is selected, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkBoxCheck() {
        if (!check.isSelected()) {
            termsCheck.setText("You have to accept the terms and conditions");
            return false;
        } else {
            termsCheck.setText("");
            return true;
        }

    }

    /**
     * This method was created to show messages every time the user select or deselect a role.
     *
     * @param event - a radio button is pressed
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void roleCheck(ActionEvent event) {
        checkRole();
    }


    /**
     * Checks if the a role field is selected and show special messages to the user.
     *
     * @return true if one role is selected, false otherwise
     */
    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkRole() {
        if (!student.isSelected() && !employee.isSelected() && !other.isSelected()) {
            roleCheck.setText("You did not choose any role");
            return false;
        } else {
            roleCheck.setText("");
            return true;
        }

    }
}

