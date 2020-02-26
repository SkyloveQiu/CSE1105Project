package nl.tudelft.oopp.group43.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterSceneController {

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
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);

    public static boolean emailValid(String email)
    {
        if(email == null)
            return false;
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    /**
     * this method check if any field is empty - exception password fi
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


    @FXML
    @SuppressWarnings("unchecked")
    private void confirmClicked(ActionEvent event)  throws IOException{
        System.out.print("Ive pressed the button");
        if (checkEmpty()== false)
            return;
        System.out.println("I can send a JSON message");
        String role;
        if (employee.isSelected())
            role = "employee";
        else if (student.isSelected())
            role = "student";
        else
            role = "other";

        JSONObject newUser = new JSONObject();
        newUser.put("firstName", (String) firstName.getText());
        newUser.put("lastName", (String) lastName.getText());
        newUser.put("username", (String) email.getText());
        newUser.put("password", (String) password.getText());
        newUser.put("role", role);


        System.out.println(newUser.toJSONString());


        //Move to a the next Scene - MainPage
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();



    }

    @SuppressWarnings("unchecked")
    @FXML
    private void backClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/LoginScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkFirstName() {
        if (firstName.getText().isEmpty()) {
            firstNameCheck.setText("You did not complete the first name field");
            return false;
        }
        else {
            firstNameCheck.setText("");
            return true;
        }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkLastName()
    {
        if(lastName.getText().isEmpty()) {
            lastNameCheck.setText("You did not complete the last name field");
            return false;
        }
        else {
            lastNameCheck.setText("");
            return true;
        }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkEmail()
    {
        if(email.getText().isEmpty()) {
            emailCheck.setText("You did not complete the email field");
            return false;
        }
        else
        {
            if(emailValid(email.getText())== false) {
                emailCheck.setText("The email is not valid");
                return false;
            }
            else {
                emailCheck.setText("");
                return true;
            }
        }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkPassword()
    {
       if(password.getText().isEmpty()) {
           passwordCheck.setText("You did not complete the password field");
           return false;
       }
       else {
           passwordCheck.setText("");
           if (cpassword.getText().isEmpty() || !password.getText().equals(cpassword.getText())) {
               cpasswordCheck.setText("The passwords don't match");
               return false;
           }
            else{
                   cpasswordCheck.setText("");
                   return true;
           }

       }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkCPassword()
    {
        if(cpassword.getText().isEmpty()) {
            cpasswordCheck.setText("You did not complete the confirm password field");
            return false;
        }
        else {
            if (password.getText().isEmpty() || !password.getText().equals(cpassword.getText())) {
                cpasswordCheck.setText("The passwords don't match");
                return false;
            }
            else {
                cpasswordCheck.setText("");
                return true;
            }
        }
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void checkBoxCheck(ActionEvent event)
    {
        checkBoxCheck();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkBoxCheck()
    {
        if(!check.isSelected()) {
            termsCheck.setText("You have to accept the terms and condtions");
            return false;
        }
        else {
            termsCheck.setText("");
            return true;
        }

    }

    @FXML
    @SuppressWarnings("unchecked")
    private void roleCheck(ActionEvent event)
    {
       checkRole();
    }


    @FXML
    @SuppressWarnings("unchecked")
    private boolean checkRole()
    {
        int s = 0;
        if(student.isSelected())
            s++;
        if(employee.isSelected())
            s++;
        if(other.isSelected())
            s++;
        if(s==1) {
            roleCheck.setText("");
            return true;
        }
        else
        {
            if(s==0)
                roleCheck.setText("You did not choose any role");
            else
                roleCheck.setText("You have to choose just one role");
            return false;
        }
    }
}

