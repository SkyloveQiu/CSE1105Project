package nl.tudelft.oopp.group43.classes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class ReservationPageContent {

    private Scene scene;
    private DatePicker datePicker;
    private String dateString;
    private boolean allowedToRun = true;
    private String[] selectedLabels;

    /**
     * Constructor that initializes the scene, datepicker, datestring and selectedLabels.
     * @param stage Stage to obtain the scene from
     */
    public ReservationPageContent(Stage stage) {
        this.scene = stage.getScene();
        datePicker = (DatePicker) scene.lookup("#datepicker");
        dateString = "";
        selectedLabels = new String[24];
    }

    /**
     * This checks if the datepicker has a date in it and if it does it loads the timetable.
     */
    public void run() {
        while (allowedToRun) {
            if (datePicker.getValue() != null) {
                LocalDate localDate = datePicker.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);

                // reloads the page only if the date gets updated
                if (!dateString.equals(date.toString())) {
                    dateString = date.toString();
                    System.out.println(dateString);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            addTimetable();
                        }
                    });
                }
            }
        }
    }

    public void stop() {
        allowedToRun = false;
    }

    /**
     * Adds the timetable to the scene when a date gets selected.
     */
    private void addTimetable() {
        GridPane timetable = (GridPane) scene.lookup("#timetable");
        // empties the selected labels
        selectedLabels = new String[24];

        // Adds all the rows to the timetable
        timetable.getRowConstraints().removeAll(timetable.getRowConstraints());

        for (int i = 0; i < 24; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(55.0);
            rc.setMinHeight(55.0);
            rc.setMaxHeight(55.0);
            rc.setFillHeight(true);

            timetable.getRowConstraints().add(rc);
        }

        for (int i = 0; i < 24; i++) {
            Label label = new Label();
            if (i < 10) {
                label.setText("0" + i + ".00");
            } else {
                label.setText(i + ".00");
            }
            label.setMinSize(1000, 50.0);
            label.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: black;");
            label.setId(Integer.toString(i));
            label.getStyleClass().add("time");

            addEventHandler(label);

            timetable.add(label, 0, i);
        }
    }

    /**
     * Adds the eventhandler to the label that handles the selecting of the label.
     * @param label Label to which to add the event
     */
    private void addEventHandler(Label label) {
        label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String labelID = label.getId();
                if (selectedLabels[Integer.parseInt(labelID)] != null && selectedLabels[Integer.parseInt(labelID)].equals(labelID)) {
                    label.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: black;");
                    selectedLabels[Integer.parseInt(labelID)] = null;
                } else {
                    label.setStyle("-fx-background-color: lightblue; -fx-border-width: 1 0 1 0; -fx-border-color: black;");
                    selectedLabels[Integer.parseInt(labelID)] = labelID;
                }
            }
        });
    }

}
