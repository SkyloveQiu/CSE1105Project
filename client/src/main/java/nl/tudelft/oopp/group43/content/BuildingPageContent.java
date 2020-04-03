package nl.tudelft.oopp.group43.content;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BuildingPageContent {

    private static Scene scene;
    private static Label[] databaseArr;
    private static Label[] labelArr;
    private static GridPane gp;
    private static ScrollPane sp;
    private static JSONArray jsonArray;

    /**
     * Adds the content to the building page.
     *
     * @param currentScene the current working scene.
     */
    public static void addContent(Scene currentScene) {
        scene = currentScene;

        if (ServerCommunication.getRole().equals("admin")) {
            ImageView add = (ImageView) scene.lookup("#add");
            ImageView edit = (ImageView) scene.lookup("#edit");
            ImageView delete = (ImageView) scene.lookup("#delete");
            add.setVisible(true);
            edit.setVisible(true);
            delete.setVisible(true);

            ColorAdjust adjust = new ColorAdjust();
            Blend blend = new Blend(BlendMode.SRC_ATOP, adjust, new ColorInput(0, 0, 40, 40, Color.SLATEGREY));
            delete.setEffect(blend);
            delete.setCache(true);
            delete.setCacheHint(CacheHint.SPEED);
            delete.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#deletePane").getStyleClass().add("crudPane");
                }
            });
            delete.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#deletePane").getStyleClass().clear();
                }
            });

            edit.setEffect(blend);
            edit.setCache(true);
            edit.setCacheHint(CacheHint.SPEED);
            edit.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#editPane").getStyleClass().add("crudPane");
                }
            });
            edit.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#editPane").getStyleClass().clear();
                }
            });

            add.setEffect(blend);
            add.setCache(true);
            add.setCacheHint(CacheHint.SPEED);
            add.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#addPane").getStyleClass().add("crudPane");
                }
            });
            add.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    scene.lookup("#addPane").getStyleClass().clear();
                }
            });
        }

        ScrollPane scp = (ScrollPane) scene.lookup("#buildings");
        sp = scp;

        createGrid();
        add();
    }

    /**
     * Adds a size listener to the scrollpane that changes the size of the building blocks.
     */
    private static void addSizeListener() {
        ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (RowConstraints rc : gp.getRowConstraints()) {
                    double newSize = ((double) newValue - 60.0) / 3;
                    rc.setPrefHeight(newSize);
                    rc.setMinHeight(newSize);
                    rc.setMaxHeight(newSize);
                }
            }
        };
        sp.widthProperty().addListener(resizeListener);
    }

    /**
     * Creates the Grid to which the buildings will be added.
     */
    private static void createGrid() {
        gp = new GridPane();
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(33.0);
        gp.getColumnConstraints().add(cc);
        gp.getColumnConstraints().add(cc);
        gp.getColumnConstraints().add(cc);
        gp.setStyle("-fx-background-color: #29293d;");
        gp.setHgap(20.0);
        gp.setVgap(20.0);
        gp.setPadding(new Insets(40));

        sp.setContent(gp);
    }

    /**
     * adds all buildings in the database to the main page.
     */
    public static void add() {
        JSONParser json = new JSONParser();

        try {
            jsonArray = (JSONArray) json.parse(ServerCommunication.getBuilding());

            labelArr = new Label[jsonArray.size()];
            databaseArr = new Label[jsonArray.size()];

            for (int i = 0; i < labelArr.length; i++) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setMinSize(360, 360);
                label.getStyleClass().add("building_labels");
                label.setStyle("-fx-background-color: lightskyblue;"
                        + "    -fx-background-radius: 15 15 15 15;"
                        + "    -fx-border-width: 1 1 1 1;"
                        + "    -fx-border-color: black;"
                        + "    -fx-border-radius: 15 15 15 15;"
                        + "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 3, 5);");

                JSONObject obj = (JSONObject) jsonArray.get(i);
                label.setText("Building " + obj.get("building_number") + ":\n" + obj.get("building_name"));
                label.setId(Long.toString((Long) obj.get("building_number")));

                labelArr[i] = label;
                databaseArr[i] = label;
            }


            addBuildings();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the buildings to the list.
     * First all children and row constraints get removed
     * Then the necessary row constraints get added back
     * And after that the building labels get added to the list/grid
     */
    public static void addBuildings() {

        while (gp.getChildren().size() > 0) {
            gp.getChildren().remove(0);
            if (gp.getRowConstraints().size() > 0) {
                gp.getRowConstraints().remove(0);
            }
        }

        int size = labelArr.length;
        double rowHeight = 360.0;
        ArrayList<RowConstraints> rcs = new ArrayList<>();
        while (size > 0) {
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(rowHeight);
            rc.setMinHeight(rowHeight);
            rc.setMaxHeight(rowHeight);
            rcs.add(rc);

            size -= 3;
        }

        gp.getRowConstraints().addAll(rcs);

        int row = 0;
        for (int i = 0; i < labelArr.length; i++) {
            if (i % 3 == 0 && i != 0) {
                row++;
            }

            gp.add(labelArr[i], i % 3, row);
        }
    }

    /**
     * Getter for the Label array.
     *
     * @return An array with all building labels
     */
    public static Label[] getLabelArr() {
        return databaseArr;
    }

    /**
     * Setter for the Label array.
     */
    public static void setLabelArr(Label[] newArray) {
        labelArr = newArray;
    }

    /**
     * Setter for the Label array from ArrayList.
     */
    public static void setLabelArr(ArrayList<Label> newArray) {
        labelArr = new Label[newArray.size()];

        for (int i = 0; i < newArray.size(); i++) {
            labelArr[i] = newArray.get(i);
        }
    }

    /**
     * Getter for the JSONArray.
     * @return the jsonarray
     */
    public static JSONArray getJsonArray() {
        return jsonArray;
    }

}
