package nl.tudelft.oopp.group43.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.content.ExceptionsPageContent;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class ExceptionsPageController {

    @FXML
    private DatePicker exceptionsDatePicker;
    @FXML
    private Label dateCheck;
    @FXML
    private ChoiceBox <String> buildings;
    @FXML
    private Label timeCheck;
    @FXML
    private RadioButton closed;
    @FXML
    private RadioButton different;
    @FXML
    private Label choiceCheck;
    @FXML
    private TextField time;


    private boolean checkDate()
    {
        LocalDate date = exceptionsDatePicker.getValue();
        if(date == null){
            dateCheck.setText("You must select an exception date");
            return false;
        }
        else
        {
            dateCheck.setText("");
            return true;
        }
    }

    private boolean timeCheck () {

      String program = time.getText();
       // if (value <= 0) {
          timeCheck.setText("");
            return true;
    //   } else {
         //  timeCheck.setText("The starting time must be equal or less than the finish time");
          //  return false;
       //}
       // return true;
    }

    private boolean checkChoice()
    {
        if(!closed.isSelected() && !different.isSelected())
        {
            choiceCheck.setText("You must choose one of these options");
            return false;
        }
        else
        {
            choiceCheck.setText("");
            return true;
        }
    }

    public void confirmException (ActionEvent event) {

        boolean ok = checkDate();
        ok = checkChoice() && ok;
        if(ok == true)
        {
            String hours = "";
            if(different.isSelected())
            {
                if(!timeCheck.getText().isEmpty())
                    return;
                hours = time.getText();
            }
            else{
                hours = "00:00-23:59";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            JSONObject obj = new JSONObject();


            LocalDate date = exceptionsDatePicker.getValue();
            String startDate = date.toString();
            String endDate = date.toString();
            String [] array = hours.split("-");
            startDate = startDate +"T" + array[0] + ":00";
            endDate = endDate + "T" + array[1] + ":00";
            String building = (String) buildings.getSelectionModel().getSelectedItem();
            Long buildingNumber = ExceptionsPageContent.getBuilding(building);
            System.out.println(startDate);
            System.out.println(endDate);
            System.out.println(buildingNumber);
            obj.put("buildingNumber", buildingNumber);
            obj.put("startingDate", startDate);
            obj.put("endDate", endDate);
            String message = ServerCommunication.sendBuildingException(obj);
            if(message == "OK"){
                System.out.println("OK");
            }
            else
            {
                System.out.println("NOT OK");
            }



        }


    }
}
