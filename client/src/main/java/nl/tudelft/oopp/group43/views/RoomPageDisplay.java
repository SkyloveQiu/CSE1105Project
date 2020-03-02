package nl.tudelft.oopp.group43.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class RoomPageDisplay {

    /**
     * Loads the Room Page for a specific building with all of its content.
     * @param stage The stage of the application we want to change the view of
     * @param buildingID The ID of the building of which we want the rooms
     * @throws IOException Throws an IOException if it cant find the FXML scene
     */
    public void start(Stage stage, String buildingID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/roomPageScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Campus Management - Room Page");
        stage.show();

        JSONParser json = new JSONParser();

        try {
            String response = ServerCommunication.getRoomsFromBuilding(buildingID);
            GridPane roomList = (GridPane) scene.lookup("#room_list");
            Label load = new Label("Loading Rooms...");
            roomList.getChildren().add(0, load);
            System.out.println(response);
            roomList.setGridLinesVisible(true);

            /*
            Checks if the rooms are in JSONArray format or in JSONObject format.
            If it is something other than this it means there was an error with the server communication.
            If that happens it shows an 'useful' error :).
             */
            if(response.charAt(0) == '[') {
                JSONArray rooms = (JSONArray) json.parse(response);

                for(int i = 0; i < rooms.size(); i++) {
                    JSONObject obj = (JSONObject) rooms.get(i);

                    Label label = new Label();
                    label.setText((String) obj.get("room_name"));
                    if(i > 0) {
                        RowConstraints rc = new RowConstraints();
                        rc.setPrefHeight(30.0);
                        rc.setMinHeight(30.0);
                        rc.setMaxHeight(30.0);
                        roomList.getRowConstraints().add(rc);
                    }else {
                        load.setText("");
                    }
                    roomList.add(label, 0, i);
                }
            } else if(response.charAt(0) == '{') {
                JSONObject obj = (JSONObject) json.parse(response);

                roomList.getChildren().add(0, new Label((String) obj.get("id")));
            } else {
                load.setText("Oops, something went wrong,\nplease check your internet connection and try again");
            }
        } catch(ParseException e) { }
    }

}
