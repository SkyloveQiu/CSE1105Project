package nl.tudelft.oopp.group43.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.views.RegisterDisplay;


import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController  {

  @FXML
  private TextField email;
  @FXML
  private PasswordField password;



   /* @FXML
    public void initialize(URL url, ResourceBundle rb)
    {

    }*/


    private String checkEmpty()
  {
      String emailString = email.getText();
      String passwordString = password.getText();

      if(emailString.isEmpty() || passwordString.isEmpty())
      {
          String message = new String();
          if(emailString.isEmpty())
              message = message + "You did not complete the email field" + "\n";
          if(passwordString.isEmpty())
              message = message + "You did not complete the password field";
          return message;
      }
      else
          return null;
  }
  @FXML
  private void loginClicked(ActionEvent event)
  {
      String messageEmpty =  checkEmpty();
      if(messageEmpty != null)
      {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setHeaderText(null);
          alert.setContentText(messageEmpty);
          alert.showAndWait();
          return;
      }

      String emailString = email.getText();
      String passwordString = password.getText();



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
