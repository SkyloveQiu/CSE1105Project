package nl.tudelft.oopp.group43.classes;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.views.RoomPageDisplay;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class MainPageContent implements Runnable {

    private Stage stage;
    private Scene scene;
    private Label[] labelArr;

    public MainPageContent(Stage stage) {
        this.stage = stage;
        this.scene = stage.getScene();
    }

    /**
     * adds all buildings in the database to the main page.
     */
    public void run() {
        JSONParser json = new JSONParser();
        GridPane gp = (GridPane) stage.getScene().lookup("#buildings_grid");
        Accordion accordion = (Accordion) stage.getScene().lookup("#building_list");

        try {
            JSONArray jsonArray = (JSONArray) json.parse(ServerCommunication.getBuilding());

            labelArr = new Label[jsonArray.size()];

            if (jsonArray.size() > 2) {
                int size = jsonArray.size();
                double rowHeight = (gp.getWidth() - 20.0) / BuildingsConfig.getColumnCount();
                while (size > BuildingsConfig.getColumnCount()) {
                    RowConstraints rc = new RowConstraints();
                    rc.setPrefHeight(rowHeight);
                    rc.setMinHeight(rowHeight);
                    rc.setMaxHeight(rowHeight);
                    gp.getRowConstraints().add(rc);
                    size -= BuildingsConfig.getColumnCount();
                }
            }

            int row = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (i % BuildingsConfig.getColumnCount() == 0 && i != 0) {
                    row++;
                }
                addLabel(i, row, gp, jsonArray, stage);
            }

            addBuildingList(labelArr, accordion, stage);

            Label label = (Label) stage.getScene().lookup("#titlebar");
            label.setText("Main Menu");

            BuildingsConfig.setLabel(labelArr);
        } catch (Exception e) {
            e.printStackTrace();
            Label label = (Label) stage.getScene().lookup("#titlebar");
            label.setText("Main Menu - Oops, something went wrong");
        }
    }

    /**
     * Adds a label to the building GridPane.
     *
     * @param i         the current index in the label-array
     * @param row       the current working row in the GridPane
     * @param gp        the GridPane to which the labels get added
     * @param jsonArray the JSONArray containing all the buildings to add
     * @param stage     the stage to pass to the room page
     */
    private void addLabel(int i, int row, GridPane gp, JSONArray jsonArray, Stage stage) {
        JSONObject obj = (JSONObject) jsonArray.get(i);
        Label label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.getStyleClass().add("building_labels");
        label.setText("Building " + obj.get("building_number") + ":\n" + obj.get("building_name"));
        label.setId(Long.toString((Long) obj.get("building_number")));

        EventHandler<MouseEvent> labelClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                RoomPageDisplay rd = new RoomPageDisplay();
                try {
                    ReservationConfig.setSelectedBuilding((Long) obj.get("building_number"));
                    rd.start(stage, Long.toString((Long) obj.get("building_number")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        label.addEventFilter(MouseEvent.MOUSE_CLICKED, labelClick);

        gp.add(label, i % BuildingsConfig.getColumnCount(), row);

        labelArr[i] = label;
    }

    /**
     * Adds the buildings to the side menu.
     *
     * @param labelArr The array containing all building labels
     * @param acc      The Accordion where to add the buildings
     * @param stage    Stage of the window
     */
    private void addBuildingList(Label[] labelArr, Accordion acc, Stage stage) {
        final TitledPane tp = acc.getPanes().get(0);
        Pane pane = new Pane();
        double pos = 0.0;

        for (int i = 0; i < labelArr.length; i++) {
            Label label = new Label();

            EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    RoomPageDisplay rd = new RoomPageDisplay();
                    try {
                        ReservationConfig.setSelectedBuilding(Long.parseLong(label.getId()));
                        rd.start(stage, label.getId());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            label.setText(labelArr[i].getText().replaceAll("\\n", " "));
            label.addEventFilter(MouseEvent.MOUSE_CLICKED, event);
            label.setPrefWidth(acc.getPrefWidth() - 20.0);
            label.getStyleClass().add("building_labels");
            label.setLayoutY(pos);

            pane.getChildren().add(label);

            pos += 20.0;
        }
        pane.setMinHeight(pos);
        pane.getStyleClass().add("menu");
        ScrollPane sp = new ScrollPane();
        sp.getStyleClass().add("list");
        sp.setContent(pane);
        sp.setPadding(new Insets(15.0, 0.0, 15.0, 0.0));
        sp.setMinSize(150.0, 250.0);
        sp.setMaxSize(300.0, 400.0);

        tp.setMinHeight(250.0);
        tp.setContent(sp);
        BuildingsConfig.setAccordionHeight(tp.getMinHeight() - 25.0);
    }
}
