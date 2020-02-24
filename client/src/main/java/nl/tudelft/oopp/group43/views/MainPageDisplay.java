package nl.tudelft.oopp.group43.views;

import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.controllers.MainPageController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainPageDisplay extends Application {

    private boolean clicked = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/mainPage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        ScrollPane sp = (ScrollPane) scene.lookup("#buildings");
        GridPane gp = (GridPane) scene.lookup("#buildings_grid");

        ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane gp = (GridPane) scene.lookup("#buildings_grid");
                for(RowConstraints rc : gp.getRowConstraints()) {
                    double newSize = ((double) newValue - 60.0)/2;
                    rc.setPrefHeight(newSize);
                    rc.setMinHeight(newSize);
                    rc.setMaxHeight(newSize);
                }
            }
        };
        sp.widthProperty().addListener(resizeListener);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Campus Management - Main Menu");
        primaryStage.show();

        addBuildings(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * adds all buildings in the database to the main page
     * @param stage the Stage where to add the buildings to
     */
    private void addBuildings(Stage stage) {
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

            if(jsonArray.size() > 2) {
                int size = jsonArray.size();
                double rowHeight = (gp.getWidth() - 20.0) / 2;
                while(size > 2) {
                    RowConstraints rc = new RowConstraints();
                    rc.setPrefHeight(rowHeight);
                    rc.setMinHeight(rowHeight);
                    rc.setMaxHeight(rowHeight);
                    gp.getRowConstraints().add(rc);
                    size -= 2;
                }
            }

            int row = 0;
            for(int i = 0; i < jsonArray.size(); i++) {
                if(i % 2 == 0 && i != 0) { row++; }
                JSONObject obj = (JSONObject) jsonArray.get(i);
                Label label = new Label();
                label.setPrefWidth(Integer.MAX_VALUE);
                label.setMaxWidth(Integer.MAX_VALUE);
                label.setMinWidth(0);
                label.setStyle("-fx-background-color: #daebeb");
                label.setText("Building " + obj.get("building_number") + ":\n" + obj.get("building_name"));

                label.addEventFilter(MouseEvent.MOUSE_CLICKED, onClick);

                gp.add(label, i % 2, row);
            }
        }
        catch(ParseException e) {}
    }
}
