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

    private static Scene scene;
    private static String dateString;
    private static String nextDay;
    private static boolean allowedToRun = true;
    private static String[] selectedLabels;
    private static String[] availableHours;
    private DatePicker datePicker;

    /**
     * Constructor that initializes the scene, datepicker, datestring and selectedLabels.
     *
     * @param stage Stage to obtain the scene from
     */
    public ReservationPageContent(Stage stage) {
        scene = stage.getScene();
        datePicker = (DatePicker) scene.lookup("#datepicker");
        dateString = "";
        nextDay = "";
        selectedLabels = new String[24];
    }

    public static void stop() {
        allowedToRun = false;
    }

    /**
     * Adds the timetable to the scene when a date gets selected.
     */
    public static void addTimetable() {
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
     *
     * @param label Label to which to add the event
     */
    private static void addEventHandler(Label label) {
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

    /**
     * This checks if the datepicker has a date in it and if it does it loads the timetable.
     */
    public void run() {
        while (allowedToRun) {
            if (datePicker.getValue() != null) {
                LocalDate date = datePicker.getValue();

                String month = Integer.toString(date.getMonthValue());
                String day = Integer.toString(date.getDayOfMonth());
                if (date.getMonthValue() < 10) {
                    month = "0" + date.getMonthValue();
                }
                if (date.getDayOfMonth() < 10) {
                    day = "0" + date.getDayOfMonth();
                }

                // reloads the page only if the date gets updated
                if (!dateString.equals(date.getYear() + "-" + month + "-" + day)) {
                    dateString = date.getYear() + "-" + month + "-" + day;
                    LocalDate newDate = date.plusDays(1);

                    month = Integer.toString(newDate.getMonthValue());
                    day = Integer.toString(newDate.getDayOfMonth());
                    if (newDate.getMonthValue() < 10) {
                        month = "0" + newDate.getMonthValue();
                    }
                    if (newDate.getDayOfMonth() < 10) {
                        day = "0" + newDate.getDayOfMonth();
                    }
                    nextDay = newDate.getYear() + "-" + month + "-" + day;

                    ReservationConfig.resetSelectedHours();

                    System.out.println(dateString + " " + nextDay);
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

    /**
     * Sets the datestring to a value (gets used to refresh the timetable after reservation).
     * @param string the string to set the dateString to
     */
    public static void setDateString(String string) {
        dateString = string;
    }

}
