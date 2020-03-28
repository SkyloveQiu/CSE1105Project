package nl.tudelft.oopp.group43.content;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import nl.tudelft.oopp.group43.classes.ThreadLock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;


public class CalendarPageContent {

    private static Scene scene;
    private static CalendarView calendarView;
    private static ArrayList<Entry> entries;
    private static int savedEntries = 0;
    private static boolean entriesAreSaved = true;
    private static boolean finishedReadingEntries = true;

    /**
     * The set up for adding content to the calendar page.
     *
     * @param currentScene the current scene of the page
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                addContent();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Adds the calendar to the page + necessary listeners, is planned to be done in a seperate thread.
     * Also listeners to the calendars get added for saving entries.
     * Also loads saved entries + if user is logged in his/her reservations
     */
    private static void addContent() {
        calendarView = new CalendarView();
        calendarView.getStylesheets().add("/stylesheets/calendarPage.css");

        entries = new ArrayList<>();
        calendarView.getCalendars().addListener((ListChangeListener.Change<? extends Calendar> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (Calendar cal : c.getAddedSubList()) {
                        cal.addEventHandler(new EventHandler<CalendarEvent>() {
                            @Override
                            public void handle(CalendarEvent event) {
                                if (event.isEntryAdded()) {
                                    Entry entry = event.getEntry();
                                    System.out.println("Entry named: " + entry.getTitle() + " was added to calendar: " + entry.getCalendar().getName() + ", " + cal.getShortName());
                                    entries.add(entry);
                                    if (finishedReadingEntries) {
                                        entriesAreSaved = false;
                                    }
                                } else if (event.isEntryRemoved()) {
                                    System.out.println("Entry named was removed from calendar: " + cal.getName() + ", " + cal.getShortName());
                                    System.out.println(entries.remove(event.getEntry()));
                                    entriesAreSaved = false;
                                }
                            }
                        });
                    }
                }
            }
        });

        Calendar calendar = new Calendar("Calendar");
        calendar.setShortName("Calendar");
        calendar.setStyle(Calendar.Style.STYLE1);

        addSavedEntries(calendar);

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

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root.getChildren().add(0, stackPane);
            }
        });
    }

    /**
     * Adds the saved entries, if any, to the calendar.
     */
    private static void addSavedEntries(Calendar calendar) {
        finishedReadingEntries = false;
        JSONParser json = new JSONParser();
        try {
            String jsonString = readSavedEntries(Paths.get("").toAbsolutePath().toString() + "\\client\\src\\main\\resources\\calendars\\entries.conf");
            System.out.println(jsonString);
            JSONArray array = (JSONArray) json.parse(jsonString);
            savedEntries = array.size();

            for (Object object : array) {
                JSONObject obj = (JSONObject) object;

                JSONObject intervalObj = (JSONObject) json.parse((String) obj.get("interval"));
                LocalDate startDate = LocalDate.parse((String) intervalObj.get("startDate"));
                LocalTime startTime = LocalTime.parse((String) intervalObj.get("startTime"));
                LocalDate endDate = LocalDate.parse((String) intervalObj.get("endDate"));
                LocalTime endTime = LocalTime.parse((String) intervalObj.get("endTime"));
                ZoneId zone = ZoneId.of((String) intervalObj.get("zoneId"));

                Interval interval = new Interval(startDate, startTime, endDate, endTime, zone);

                Entry entry = new Entry((String) obj.get("title"), interval);
                entry.setCalendar(calendar);
                entry.setId((String) obj.get("id"));
                entry.setLocation((String) obj.get("location"));
                entry.setFullDay((boolean) obj.get("fullDay"));

                calendar.addEntry(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        finishedReadingEntries = true;
    }

    /**
     * Reads the entries in a specified source.
     *
     * @param source the source
     * @return the contents of this source as a json array string.
     */
    private static String readSavedEntries(String source) throws FileNotFoundException {
        File file = new File(source);

        System.out.println(file.exists());
        if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            if (stringBuilder.length() > 1) {
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]");
            } else {
                stringBuilder.append("]");
            }

            return stringBuilder.toString();
        } else {
            return "[]";
        }
    }

    /**
     * When this method is called all entries of the calendar will get saved.
     */
    public static void saveEntries() {
        ThreadLock.flag = 1;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String path = Paths.get("").toAbsolutePath().toString() + "\\client\\src\\main\\resources\\calendars\\";
                // Use relative path for Unix systems
                File temp = new File(path + "temp-entries.conf");
                File file = new File(path + "entries.conf");

                temp.getParentFile().mkdirs();
                try {
                    temp.createNewFile();

                    for (Entry entry : entries) {
                        JSONObject obj = new JSONObject();

                        mapEntryToJson(entry, obj);

                        try {
                            System.out.println("Trying to write to: " + Paths.get("").toAbsolutePath().toString() + "\\client\\src\\main\\resources\\calendars\\temp-entries.conf");
                            saveJsonToFile(obj, Paths.get("").toAbsolutePath().toString() + "\\client\\src\\main\\resources\\calendars\\temp-entries.conf");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    file.delete();
                    System.out.println("succeeded = " + temp.renameTo(file));
                    temp.delete();
                    entriesAreSaved = true;
                    ThreadLock.flag = 0;
                } catch (IOException e) {
                    ThreadLock.flag = 0;
                    entriesAreSaved = true;
                    e.printStackTrace();
                }
            }
        });
        // We want this thread to prevent JVM shutdown so that it can fullfill its task
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * This method maps the Entry of the calendar to a json object for storing for later use.
     *
     * @param entry the entry to map
     * @param obj   the Json object containing all necessary fields of the entry:
     *              - id (String)
     *              - title (String)
     *              - calendarName (String)
     *              - interval (JSONObject)
     *              - location (String)
     *              - fullDay (boolean)
     *              - multiDay (boolean)
     *              - recurrence (boolean)
     *              - recurrenceId (String)
     */
    private static void mapEntryToJson(Entry entry, JSONObject obj) {
        obj.put("id", entry.getId());
        obj.put("title", entry.getTitle());
        obj.put("calendarName", entry.getCalendar().getName());
        obj.put("interval",  intervalToJson(entry.getInterval()));
        obj.put("location", entry.getLocation());
        obj.put("fullDay", entry.isFullDay());
        obj.put("multiDay", entry.isMultiDay());
        obj.put("recurrence", entry.isRecurrence());
        obj.put("recurrenceid", entry.getRecurrenceId());
    }

    /**
     * Maps the Interval of calendarFX to a Json object for storing it.
     *
     * @param interval the interval to map to json
     * @return A json object with fields:
     * - zoneId
     * - startDate
     * - startTime
     * - endDate
     * - endTime
     */
    private static String intervalToJson(Interval interval) {
        StringBuilder json = new StringBuilder();
        json.append("{\"zoneId\":\"");

        json.append(interval.getZoneId().toString() + "\",\"startDate\":\"");
        json.append(interval.getStartDate().toString() + "\",\"startTime\":\"");
        json.append(interval.getStartTime().toString() + "\",\"endDate\":\"");
        json.append(interval.getEndDate().toString() + "\",\"endTime\":\"");
        json.append(interval.getEndTime().toString() + "\"}");

        return json.toString();
    }

    /**
     * Writes and saves the json object to a file.
     *
     * @param obj  the json object to save
     * @param file the location of the file
     * @throws IOException when something went wrong with writing to the file
     */
    private static void saveJsonToFile(JSONObject obj, String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.print(obj.toJSONString() + ",");
        printWriter.close();

        System.out.println("Successfully wrote Object:\n" + obj.toJSONString() + "\nto the file:" + file);
    }

    /**
     * Checks if entries had been added to the calendars.
     *
     * @return true if entries have been added, false if otherwise
     */
    public static boolean areEntriesAdded() {
        System.out.println("Are entries added?: " + (entries.size() != savedEntries));
        return entries.size() != savedEntries;
    }

    public static boolean areEntriesAddedAndNotSaved() {
        return areEntriesAdded() && !entriesAreSaved;
    }

}
