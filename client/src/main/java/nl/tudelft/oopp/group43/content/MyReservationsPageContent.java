package nl.tudelft.oopp.group43.content;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyReservationsPageContent {

    private static Scene scene;
    private static JSONArray jsonArray;
    private static Label[] labelArr;
    private static GridPane gp;

    public static void addContent(Scene currentScene) {
        scene = currentScene;
        addReservations();
    }

    private static void addReservations() {
        JSONParser json = new JSONParser();
        try {
            jsonArray = (JSONArray) json.parse(ServerCommunication.getReservations());
            labelArr = new Label[jsonArray.size()];
            gp = new GridPane();
            for(int i=0; i < jsonArray.size(); i++){
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setMinSize(360, 360);
                //label.getStyleClass().add("building_labels");
                label.setStyle("-fx-background-color: lightskyblue;"
                        + "    -fx-background-radius: 15 15 15 15;"
                        + "    -fx-border-width: 1 1 1 1;"
                        + "    -fx-border-color: black;"
                        + "    -fx-border-radius: 15 15 15 15;"
                        + "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 3, 5);");

                JSONObject obj = (JSONObject) jsonArray.get(i);
                JSONObject user = (JSONObject) obj.get("user");
                String name = (String) user.get("first_name") + " " + user.get("last_name");
                label.setText("Reservation of room " + obj.get("room_id") + ":\n" + name);
                label.setId(Long.toString((Long) obj.get("reservationId")));
                labelArr[i] = label;

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
