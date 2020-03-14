package nl.tudelft.oopp.group43.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.ArrayList;

public class RoomPageContent {

    private static Scene scene;
    private static LocalDate date;
    private static String hoursFrom;
    private static String hoursTil;

    private AnchorPane ap;
    private DatePicker datepicker;
    private ChoiceBox<String> fromTime;
    private ChoiceBox<String> untilTime;

    public RoomPageContent(Scene currentScene) {
        scene = currentScene;
        date = null;
        hoursFrom = "";
        hoursTil = "";

        ap = (AnchorPane) scene.lookup("#timeDateSelect");
        datepicker = (DatePicker) scene.lookup("#date");
        fromTime = (ChoiceBox<String>) scene.lookup("#fromTime");
        untilTime = (ChoiceBox<String>) scene.lookup("#untilTime");
    }

    public void addContent() {
        addChoiceboxContent();
        addDatepickerListener();
    }

    private void addChoiceboxContent() {
        ArrayList<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hours.add("0" + Integer.toString(i) + ":00");
            } else {
                hours.add(Integer.toString(i) + ":00");
            }
        }
        ObservableList<String> list = FXCollections.observableArrayList(hours);
        fromTime.setItems(list);
        untilTime.setItems(list);

        addChoiceboxListener();
    }

    private void addChoiceboxListener() {
        fromTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setHoursFrom(fromTime.getItems().get((Integer) number2));

                if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                    ap.setVisible(false);
                }
            }
        });
        untilTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                setHoursTil(untilTime.getItems().get((Integer) number2));

                if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                    ap.setVisible(false);
                }
            }
        });
    }

    private void addDatepickerListener() {
        datepicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            setDate(newValue);

            if (!hoursFrom.equals("") && !hoursTil.equals("") && date != null) {
                ap.setVisible(false);
            }
        });
    }

    public static void setDate(LocalDate date) {
        RoomPageContent.date = date;
    }

    public static void setHoursFrom(String hoursFrom) {
        RoomPageContent.hoursFrom = hoursFrom;
    }

    public static void setHoursTil(String hoursTil) {
        RoomPageContent.hoursTil = hoursTil;
    }
}
