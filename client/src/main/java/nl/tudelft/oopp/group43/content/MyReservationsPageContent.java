package nl.tudelft.oopp.group43.content;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyReservationsPageContent {

    private static Scene scene;
    private static JSONArray jsonArray;
    private static GridPane gp;

    /**
     * Adds content to the My Reservations Page.
     *
     * @param currentScene the current scene.
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addReservations();
        ScrollPane sp = (ScrollPane) scene.lookup("#reservationScroll");
        sp.setContent(gp);
    }

    /**
     * Adds the reservations to the gridpane.
     */
    private static void addReservations() {
        gp = (GridPane) scene.lookup("#reservations");
        JSONParser jsonParser = new JSONParser();
        try {
            jsonArray = (JSONArray) jsonParser.parse(ServerCommunication.getReservationsByUser());
            createGrid();
            for (int i = 0; i < jsonArray.size(); i++) {
                AnchorPane reservation = new AnchorPane();
                //reservation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                Label timeLabel = new Label();
                Label roomLabel = new Label();
                timeLabel.getStyleClass().add("time-label");
                roomLabel.getStyleClass().add("reservation-label");
                reservation.getStyleClass().add("reservation-box");
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String startDate = getFancyText((String) obj.get("starting_date"));
                String endDate = getFancyText((String) obj.get("end_date"));
                timeLabel.setText("The room is reserved from " + startDate + " to " + endDate);
                roomLabel.setText("Room number " + obj.get("room_id"));

                AnchorPane.setLeftAnchor(timeLabel, 50.0);
                AnchorPane.setLeftAnchor(roomLabel, 50.0);

                AnchorPane.setTopAnchor(roomLabel, 10.0);
                AnchorPane.setTopAnchor(timeLabel, 50.0);

                reservation.getChildren().add(roomLabel);
                reservation.getChildren().add(timeLabel);
                gp.add(reservation, 0, i);

                //edit button
                Button editButton = new Button();
                editButton.setMinSize(75.0, 75.0);
                editButton.setPrefSize(75.0, 75.0);
                editButton.setMaxSize(75.0, 75.0);
                editButton.getStyleClass().add("button");
                ImageView img = new ImageView(new Image("/icons/edit-icon.png"));
                img.setFitHeight(40.0);
                img.setFitWidth(40.0);
                editButton.setGraphic(img);

                //cancel button
                Button cancelButton = new Button();
                cancelButton.setMinSize(75.0, 75.0);
                cancelButton.setPrefSize(75.0, 75.0);
                cancelButton.setMaxSize(75.0, 75.0);
                cancelButton.getStyleClass().add("button");
                ImageView img2 = new ImageView(new Image("/icons/delete-icon.png"));
                img2.setFitHeight(40.0);
                img2.setFitWidth(40.0);
                cancelButton.setGraphic(img2);

                //vbox
                VBox box = new VBox();
                box.getChildren().add(editButton);
                box.getChildren().add(cancelButton);
                gp.add(box, 1, i);


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the empty grid.
     */
    private static void createGrid() {
        RowConstraints rr = new RowConstraints();
        rr.setMaxHeight(150.0);
        rr.setMinHeight(150.0);
        rr.setPrefHeight(150.0);
        int constraints = jsonArray.size();
        for (int i = 0; i < constraints; i++) {
            gp.getRowConstraints().add(rr);
        }
        gp.setVgap(20.0);
    }

    /**
     * A method to transform a date-time into a more user-friendly text.
     *
     * @param date the date to be transformed into fancy text
     * @return the date in a fancier way: 12-04-2001 10:00 will be returned as 'the 12th of April 10:00'
     */
    private static String getFancyText(String date) {
        String dateMonth = date.substring(5, 7);
        String dateDay = date.substring(8, 10);
        String dateYear = date.substring(0, 4);
        String dateHour = date.substring(11, 16);
        switch (dateMonth) {
            case "01":
                dateMonth = "January";
                break;
            case "02":
                dateMonth = "February";
                break;
            case "03":
                dateMonth = "March";
                break;
            case "04":
                dateMonth = "April";
                break;
            case "05":
                dateMonth = "May";
                break;
            case "06":
                dateMonth = "June";
                break;
            case "07":
                dateMonth = "July";
                break;
            case "08":
                dateMonth = "August";
                break;
            case "09":
                dateMonth = "September";
                break;
            case "10":
                dateMonth = "Oktober";
                break;
            case "11":
                dateMonth = "November";
                break;
            case "12":
                dateMonth = "December";
                break;
            default:
                dateMonth = "NaMonth";

        }
        int day = Integer.parseInt(dateDay);
        if ((day == 11 || day == 12) || day == 13) {
            dateDay = dateDay + "th";
        } else {
            switch (day % 10) {
                case 1:
                    dateDay = dateDay + "st";
                    break;
                case 2:
                    dateDay = dateDay + "nd";
                    break;
                case 3:
                    dateDay = dateDay + "rd";
                    break;
                default:
                    dateDay = dateDay + "th";
            }
        }

        return "the " + dateDay + " of " + dateMonth + " " + dateHour;
    }
}
