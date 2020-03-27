package nl.tudelft.oopp.group43.content;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.time.LocalTime;

public class CalendarPageContent {

    private static Scene scene;

    public static void addContent(Scene currentScene) {
        scene = currentScene;

        CalendarView calendarView = new CalendarView();
        calendarView.getStylesheets().add("/stylesheets/calendarPage.css");
        calendarView.showDeveloperConsoleProperty().setValue(true);

        Calendar calendar = new Calendar("Calendar");
        calendar.setShortName("Calendar");
        calendar.setStyle(Calendar.Style.STYLE1);

        CalendarSource calendarSource = new CalendarSource("My Calendars");
        calendarSource.getCalendars().add(calendar);

        calendarView.getCalendarSources().setAll(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(calendarView);
        AnchorPane.setLeftAnchor(stackPane, 88.0);
        AnchorPane.setTopAnchor(stackPane, 141.0);
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);

        AnchorPane root = (AnchorPane) scene.lookup("#root");
        root.getChildren().add(0, stackPane);
    }

}
