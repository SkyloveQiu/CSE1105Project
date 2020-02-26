package nl.tudelft.oopp.group43.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.views.RegisterDisplay;


import javafx.event.ActionEvent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static nl.tudelft.oopp.group43.controllers.RegisterSceneController.emailValid;

public class LoginSceneController  implements Initializable{

  @FXML
  private TextField email;
  @FXML
  private PasswordField password;
  @FXML
  private Label emailCheck;
  @FXML
  private Label passwordCheck;



    private boolean checkEmpty()
  {

       boolean okEmpty = emptyEmail();
       okEmpty = (emptyPassword() && okEmpty );
       return okEmpty;
  }


  @FXML
  @SuppressWarnings("unchecked")
  private void loginClicked(ActionEvent event)
  {//  System.out.println("am intrat in functie");
      boolean okEmpty =  checkEmpty();
     if(okEmpty == false)
         return;

     //String emailString = email.getText();
     // String passwordString = password.getText();
      JSONObject newUser = new JSONObject();
      newUser.put("username", (String)email.getText());
      newUser.put("password", (String)password.getText());

      System.out.println(newUser.toJSONString());

     // emailCheck.setText("The password or the username is wrong");
      System.out.println("The final of the function");





  }

  @FXML
  @SuppressWarnings("unchecked")
  private boolean emptyEmail() {
      email.getText();
      if (email.getText().isEmpty()) {
          emailCheck.setText("You did not complete the email field");
          return false;
      }

      else {
          if (emailValid(email.getText()) == false) {
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
  private boolean emptyPassword()
  {
      String passwordString = password.getText();
      if(passwordString.isEmpty()) {
          passwordCheck.setText("You did not complete the password field");
          return false;
      }
      else {
          passwordCheck.setText("");
          return true;
      }

  }


    @SuppressWarnings("unchecked")
    @FXML
    private void registerClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/RegisterScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //emailCheck.setText("");
    }


}
