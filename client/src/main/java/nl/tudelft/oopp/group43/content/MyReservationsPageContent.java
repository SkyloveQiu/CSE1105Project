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
                reservation.setPrefSize(400,200);
                Label userLabel = new Label();
                JSONObject user = (JSONObject) obj.get("user");
                String firstName = (String) user.get("first_name");
                String lastName = (String) user.get("last_name");
                String userName = (String) user.get("username");
                userLabel.setText(firstName + lastName + "\n" + userName);
                
                reservation.getChildren().add(userLabel);

                gp.add(reservation, 0, i);



            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void createGrid() {
        int constraints = jsonArray.size();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100);
        gp.getColumnConstraints().add(cc);

        RowConstraints rr = new RowConstraints();
        rr.setPercentHeight(100/constraints);
        for(int i = 0; i < constraints; i++){
            gp.getRowConstraints().add(rr);
        }

        gp.setStyle("-fx-background-color: black;");
    }
}
