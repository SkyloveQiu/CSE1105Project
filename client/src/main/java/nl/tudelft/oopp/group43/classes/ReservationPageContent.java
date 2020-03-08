package nl.tudelft.oopp.group43.classes;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;

public class ReservationPageContent {

    private Scene scene;
    private DatePicker datePicker;
    private String dateString;
    private String nextDay;
    private boolean allowedToRun = true;
    private String[] selectedLabels;
    private String[] availableHours;

    /**
     * Constructor that initializes the scene, datepicker, datestring and selectedLabels.
     * @param stage Stage to obtain the scene from
     */
    public ReservationPageContent(Stage stage) {
        this.scene = stage.getScene();
        datePicker = (DatePicker) scene.lookup("#datepicker");
        dateString = "";
        nextDay = "";
        selectedLabels = new String[24];
    }

    /**
     * This checks if the datepicker has a date in it and if it does it loads the timetable.
     */
    public void run() {
        while (allowedToRun) {
            if (datePicker.getValue() != null) {
                LocalDate date = datePicker.getValue();

                String month = Integer.toString(date.getMonthValue());
                String day = Integer.toString(date.getDayOfMonth());
                if (date.getMonthValue() < 10) { month = "0" + date.getMonthValue(); }
                if (date.getDayOfMonth() < 10) { day = "0" + date.getDayOfMonth(); }

                // reloads the page only if the date gets updated
                if (!dateString.equals(date.getYear() + "-" + month + "-" + day)) {
                    dateString = date.getYear() + "-" + month + "-" + day;
                    date.plusDays(1);

                    month = Integer.toString(date.getMonthValue());
                    day = Integer.toString(date.getDayOfMonth());
                    if (date.getMonthValue() < 10) { month = "0" + date.getMonthValue(); }
                    if (date.getDayOfMonth() < 10) { day = "0" + date.getDayOfMonth(); }
                    nextDay = date.getYear() + "-" + month + "-" + day;
                    System.out.println(dateString);

                    ReservationConfig.resetSelectedHours();

                    availableHours = ServerCommunication.getAvailableRoomHours(ReservationConfig.getSelectedRoom(), dateString, nextDay);

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
        timetable.getChildren().removeAll(timetable.getChildren());
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
            String id = "";
            if (i < 10) {
                label.setText("0" + i + ".00");
                id = "0" + i;
            } else {
                label.setText(i + ".00");
                id = Integer.toString(i);
            }
            label.setMinSize(1000, 50.0);
            label.setStyle("-fx-border-width: 1 0 1 0; -fx-border-color: black;");
            label.setId(id);
            label.getStyleClass().add("time");

            if (availableHours[i].equals("free")) {
                addEventHandler(label);
            } else {
                label.setStyle("-fx-background-color: lightcoral; -fx-border-width: 1 0 1 0; -fx-border-color: black;");
            }

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

                    ReservationConfig.removeHour(dateString + "-" + labelID);
                } else {
                    label.setStyle("-fx-background-color: lightblue; -fx-border-width: 1 0 1 0; -fx-border-color: black;");
                    selectedLabels[Integer.parseInt(labelID)] = labelID;

                    ReservationConfig.addHour(dateString + "-" + labelID);
                }
            }
        });
    }

}
