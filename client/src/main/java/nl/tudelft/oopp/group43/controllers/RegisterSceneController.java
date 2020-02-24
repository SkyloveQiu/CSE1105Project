package nl.tudelft.oopp.group43.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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


    /**
     * this method check if any field is empty - exception password fi
     */
    private boolean checkEmpty()
    {
       String message = new String();
       String passwordString = password.getText();
       String cpasswordString = cpassword.getText();
       String lastNameString = lastName.getText();
       String firstNameString = firstName.getText();
       String emailString = email.getText();
       int s = 0;
       boolean empty = false;
       if(!student.isSelected())
           s++;
       if(!employee.isSelected())
           s++;
       if(!other.isSelected())
           s++;
       System.out.println(s);

       if(passwordString.isEmpty() || cpasswordString.isEmpty() || lastNameString.isEmpty() || firstNameString.isEmpty() || emailString.isEmpty())
       {
           empty = true;
          // if(firstNameString.isEmpty())
            ///   message = message + "You did not complete the first name field" + "\n";
        ///   if(lastNameString.isEmpty())
           //    message = message + "You did not complete the last name  field" + "\n";
           if(emailString.isEmpty())
               emailCheck.setText("You did not complete the email field");
           else
               emailCheck.setText("");
           if(passwordString.isEmpty())
               passwordCheck.setText("You did not complete the password field");
           else
               passwordCheck.setText("");
           if(cpasswordString.isEmpty())
               cpasswordCheck.setText( "You did not confirm password field" );
           else
           {
               if(passwordString.isEmpty() || passwordString.equals(cpasswordString) == false)
                   cpasswordCheck.setText( "The passwords don't match");
               else
                   cpasswordCheck.setText("");

           }
       }
       if(s!=2)
       {
           empty = true;
           if(s==3)
              roleCheck.setText("You did not choose any role");
           else
               roleCheck.setText("You have to choose just one role");
       }
       else
           roleCheck.setText("");

        if(!check.isSelected()) {
            termsCheck.setText("You have to accept the terms and condtions");
            empty = true;
        }
        else
            termsCheck.setText("");

        return empty;
    }

    private boolean passwordMatch()
    {
        String passwordString = password.getText();
        String cpasswordString = cpassword.getText();

        return passwordString.equals(cpasswordString);
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void confirmClicked(ActionEvent event)
    {
       // boolean okEmpty = checkEmpty();
        if(checkEmpty() == true)
           return;


     /* if(checkEmpty()== true)
          return;

      if(passwordMatch() == false)
      {
        //  password.clear();
        //  cpassword.clear();

          return;
      }
      else
          cpasswordCheck.setText("");*/






        String role;
        if(employee.isSelected())
            role = "employee";
        else if(student.isSelected())
            role = "student";
        else
            role = "other";

        JSONObject newUser = new JSONObject();
        newUser.put("FirstName", (String)firstName.getText());
        newUser.put("LastName", (String) lastName.getText());
        newUser.put("Email", (String)email.getText());
        newUser.put("Password", (String)password.getText());
        newUser.put("Role", role);



        System.out.println(newUser.toJSONString());



    }


}
