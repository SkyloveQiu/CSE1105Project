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

public class LoginSceneController  {

  @FXML
  private TextField email;
  @FXML
  private PasswordField password;
  @FXML
  private Label emailCheck;
  @FXML
  private Label passwordCheck;


   /* @FXML
    public void initialize(URL url, ResourceBundle rb)
    {

    }*/


    private boolean checkEmpty()
  {
      String emailString = email.getText();
      String passwordString = password.getText();

      boolean okEmpty = false;

      if(emailString.isEmpty() || passwordString.isEmpty())
      {
          if(emailString.isEmpty())
              emailCheck.setText("You did not complete the email field");
          else
              emailCheck.setText("");
          if(passwordString.isEmpty())
             passwordCheck.setText("You did not complete the password field");
          else
             passwordCheck.setText("");
          okEmpty = true;
      }
     return okEmpty;
  }
  @FXML
  @SuppressWarnings("unchecked")
  private void loginClicked(ActionEvent event)
  {
      boolean okEmpty =  checkEmpty();
     if(okEmpty == true)
         return;

     // String emailString = email.getText();
     // String passwordString = password.getText();
      JSONObject newUser = new JSONObject();
      newUser.put("Email", (String)email.getText());
      newUser.put("Password", (String)password.getText());




  }


    //private Scene loginScene;

   /* private void registerClicked()
    {
        System.out.println("mamaiNDN");
    }*/


    /*@SuppressWarnings("unchecked")
    public void registerClicked()
    {

       // RegisterDisplay.main(new String [0]);
      // System.out.println("am ajuns aici");
        Parent nextRegisteView = FXMLLoader.load(getClass().getResource("/RegisterScene.fxml"));
        Scene nextRegisterScene = new Scene (nextRegisteView);

       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(nextRegisterScene);
        window.show();

      System.out.println("amiwenfi");
      
   }*/

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


}
