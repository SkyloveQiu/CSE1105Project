package nl.tudelft.oopp.group43.content;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyReservationsPageContent {

    private static Scene scene;
    private static JSONArray jsonArray;
    private static GridPane gp;

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addReservations();
    }

    private static void addReservations() {
        gp = (GridPane) scene.lookup("#reservations");
        JSONParser jsonParser = new JSONParser();
        try {
            jsonArray = (JSONArray) jsonParser.parse(ServerCommunication.getReservationsByUser());
            createGrid();
            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject obj = (JSONObject) jsonArray.get(i);
                AnchorPane reservation = new AnchorPane();
                reservation.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                Label userLabel = new Label();
                Label timeLabel = new Label();
                Label roomLabel = new Label();
                userLabel.getStyleClass().add("reservation-label");
                timeLabel.getStyleClass().add("reservation-label");
                roomLabel.getStyleClass().add("reservation-label");
                reservation.getStyleClass().add("reservation-box");
                JSONObject user = (JSONObject) obj.get("user");
                userLabel.setText("Reserved by: " + user.get("first_name") + " " + user.get("last_name") + "\n" + user.get("username"));
                timeLabel.setText("Reserved from " + obj.get("starting_date") + " to " + obj.get("end_date"));
                roomLabel.setText("Room number " + obj.get("room_id"));

                AnchorPane.setLeftAnchor(userLabel, 50.0);
                AnchorPane.setLeftAnchor(timeLabel, 50.0);
                AnchorPane.setLeftAnchor(roomLabel, 50.0);

                AnchorPane.setTopAnchor(roomLabel, 10.0);
                AnchorPane.setTopAnchor(timeLabel, 30.0);
                AnchorPane.setTopAnchor(userLabel, 50.0);

                reservation.getChildren().add(userLabel);
                reservation.getChildren().add(roomLabel);
                reservation.getChildren().add(timeLabel);
                gp.add(reservation, 0, i);



            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void createGrid() {
        int constraints = jsonArray.size();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(90);
        gp.getColumnConstraints().add(cc);
        ColumnConstraints kk = new ColumnConstraints();
        kk.setPercentWidth(10);
        gp.getColumnConstraints().add(kk);

        RowConstraints rr = new RowConstraints();
        rr.setPercentHeight(100/constraints);
        for(int i = 0; i < constraints; i++){
            gp.getRowConstraints().add(rr);
        }

        gp.setStyle("-fx-background-color: aliceblue;");
        gp.setVgap(20.0);
    }
}
