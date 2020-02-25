package nl.tudelft.oopp.group43.classes;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

public class MainPageContent implements Runnable {

    private Stage stage;
    private Scene scene;
    private Label[] labelArr;

    public MainPageContent(Stage stage) {
        this.stage = stage;
        this.scene = stage.getScene();
    }

    /**
     * adds all buildings in the database to the main page
     */
    public void run() {
        JSONParser json = new JSONParser();
        GridPane gp = (GridPane) stage.getScene().lookup("#buildings_grid");

        try {
            JSONArray jsonArray = (JSONArray) json.parse(ServerCommunication.getBuilding());
            EventHandler<MouseEvent> onClick = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    FXMLLoader loader = new FXMLLoader();
                    URL xmlUrl = getClass().getResource("/roomPageScene.fxml");
                    loader.setLocation(xmlUrl);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            };

            labelArr = new Label[jsonArray.size()];

            if(jsonArray.size() > 2) {
                int size = jsonArray.size();
                double rowHeight = (gp.getWidth() - 20.0) / MainPageConfig.getColumnCount();
                while(size > MainPageConfig.getColumnCount()) {
                    RowConstraints rc = new RowConstraints();
                    rc.setPrefHeight(rowHeight);
                    rc.setMinHeight(rowHeight);
                    rc.setMaxHeight(rowHeight);
                    gp.getRowConstraints().add(rc);
                    size -= MainPageConfig.getColumnCount();
                }
            }

            int row = 0;
            for(int i = 0; i < jsonArray.size(); i++) {
                if(i % MainPageConfig.getColumnCount() == 0 && i != 0) { row++; }
                addLabel(i, row, gp, jsonArray, onClick);
            }

            Label label = (Label) stage.getScene().lookup("#titlebar");
            label.setText("Main Menu");

            MainPageConfig.setLabel(labelArr);
        }
        catch(ParseException e) {}
    }

    private void addLabel(int i, int row, GridPane gp, JSONArray jsonArray, EventHandler<MouseEvent> onClick) {
        JSONObject obj = (JSONObject) jsonArray.get(i);
        Label label = new Label();
        label.setPrefWidth(Integer.MAX_VALUE);
        label.setMaxWidth(Integer.MAX_VALUE);
        label.setMinWidth(0);
        label.setStyle("-fx-background-color: #daebeb");
        label.setText("Building " + obj.get("building_number") + ":\n" + obj.get("building_name"));

        label.addEventFilter(MouseEvent.MOUSE_CLICKED, onClick);

        gp.add(label, i % MainPageConfig.getColumnCount(), row);

        labelArr[i] = label;
    }
}
