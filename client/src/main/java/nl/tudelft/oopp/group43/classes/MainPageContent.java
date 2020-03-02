package nl.tudelft.oopp.group43.classes;

import java.io.IOException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        Pane menuPane = (Pane) stage.getScene().lookup("#menubar");

        try {
            JSONArray jsonArray = (JSONArray) json.parse(ServerCommunication.getBuilding());
            EventHandler<MouseEvent> labelClick = new EventHandler<MouseEvent>() {
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

            EventHandler<MouseEvent> accordionClick = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (!MainPageConfig.isAccordionExpanded()) {
                        for (Node node : menuPane.getChildren()) {
                            if (!node.equals(accordion)) {
                                node.relocate(node.getLayoutX(), node.getLayoutY() + MainPageConfig.getAccordionHeight());
                                MainPageConfig.setAccordionExpanded(true);
                            }
                        }
                    } else if (MainPageConfig.isAccordionExpanded()) {
                        for (Node node : menuPane.getChildren()) {
                            if (!node.equals(accordion)) {
                                node.relocate(node.getLayoutX(), node.getLayoutY() - MainPageConfig.getAccordionHeight());
                                MainPageConfig.setAccordionExpanded(false);
                            }
                        }
                    }
                }
            };
            accordion.addEventFilter(MouseEvent.MOUSE_CLICKED, accordionClick);

            labelArr = new Label[jsonArray.size()];

            if (jsonArray.size() > 2) {
                int size = jsonArray.size();
                double rowHeight = (gp.getWidth() - 20.0) / MainPageConfig.getColumnCount();
                while (size > MainPageConfig.getColumnCount()) {
                    RowConstraints rc = new RowConstraints();
                    rc.setPrefHeight(rowHeight);
                    rc.setMinHeight(rowHeight);
                    rc.setMaxHeight(rowHeight);
                    gp.getRowConstraints().add(rc);
                    size -= MainPageConfig.getColumnCount();
                }
            }

            int row = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (i % MainPageConfig.getColumnCount() == 0 && i != 0) {
                    row++;
                }
                addLabel(i, row, gp, jsonArray, labelClick);
            }

            addBuildingList(labelArr, accordion, labelClick);

            Label label = (Label) stage.getScene().lookup("#titlebar");
            label.setText("Main Menu");

            MainPageConfig.setLabel(labelArr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a label to the building GridPane.
     *
     * @param i         the current index in the label-array
     * @param row       the current working row in the GridPane
     * @param gp        the GridPane to which the labels get added
     * @param jsonArray the JSONArray containing all the buildings to add
     * @param onClick   the Handler that gets added to the labels and activates when clicked on
     */
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

    /**
     * Adds the buildings to the side menu.
     *
     * @param labelArr The array containing all building labels
     * @param acc      The Accordion where to add the buildings
     * @param event    Event that happens when the Label gets clicked
     */
    private void addBuildingList(Label[] labelArr, Accordion acc, EventHandler<MouseEvent> event) {
        final TitledPane tp = acc.getPanes().get(0);
        Pane pane = new Pane();
        double pos = 0.0;

        for (int i = 0; i < labelArr.length; i++) {
            Label label = new Label();

            label.setText(labelArr[i].getText().replaceAll("\\n", " "));
            label.addEventFilter(MouseEvent.MOUSE_CLICKED, event);
            label.setPrefWidth(acc.getPrefWidth());
            label.setMaxWidth(acc.getPrefWidth());
            label.setMinWidth(0);
            label.setStyle("-fx-background-color: #daebeb");
            label.setLayoutY(pos);

            pane.getChildren().add(label);

            pos += 20.0;
        }
        pane.setMinHeight(pos);
        ScrollPane sp = new ScrollPane();
        sp.setContent(pane);
        sp.setPadding(new Insets(18.0, 0.0, 0.0, 0.0));
        sp.setLayoutY(18.0);
        sp.setMinSize(150.0, 250.0);
        sp.setMaxSize(150.0, 250.0);

        tp.setMinHeight(250.0);
        tp.setContent(sp);
        MainPageConfig.setAccordionHeight(tp.getMinHeight());
    }
}
