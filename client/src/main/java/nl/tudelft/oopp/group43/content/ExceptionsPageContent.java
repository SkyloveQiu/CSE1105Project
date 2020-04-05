package nl.tudelft.oopp.group43.content;

import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class ExceptionsPageContent {

    private static Scene scene;

    public static void addContent(Scene currentScene){
        scene = currentScene;
        addDatePicker();
    }

    private static void addDatePicker() {
        DatePicker startingDatePicker = (DatePicker) scene.lookup("#exceptionsDatePicker");
        startingDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        System.out.println("datepicker added");
    }
}
