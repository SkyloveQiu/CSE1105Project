package nl.tudelft.oopp.group43.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

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

    public void confirmExpection (ActionEvent event) {

        boolean ok = checkDate();
        //ok = checkTime() && ok;
        ok = checkChoice() && ok;
        if(ok == true)
        {
            LocalDate date = exceptionsDatePicker.getValue();
            System.out.println(date);
            String building = (String) buildings.getSelectionModel().getSelectedItem();
             System.out.println(building);
         //   String startTime = (String) fromTime.getSelectionModel().getSelectedItem();
          //  String finishTime = (String) untilTime.getSelectionModel().getSelectedItem();
         //   System.out.println(startTime);
          //  System.out.println(finishTime);

        }


    }
}
