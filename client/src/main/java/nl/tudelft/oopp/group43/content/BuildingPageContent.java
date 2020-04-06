package nl.tudelft.oopp.group43.content;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.tudelft.oopp.group43.classes.BuildingMap;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BuildingPageContent {

    private static Scene scene;
    private static Label[] databaseArr;
    private static Label[] labelArr;
    private static GridPane gp;
    private static JSONArray jsonArray;
    private static AnchorPane infoPane;
    private static double infoPaneY;

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

        infoPane = (AnchorPane) scene.lookup("#infoPane");

        createGrid();
        add();
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

        ((ScrollPane) scene.lookup("#buildings")).setContent(gp);
        //((Pane) scene.lookup("#scrollContent")).getChildren().remove(1);
    }

    /**
     * adds all buildings in the database to the main page.
     */
    public static void add() {
        SceneLoader.configureBuildingMap();

        JSONParser json = new JSONParser();

        try {
            jsonArray = (JSONArray) json.parse(ServerCommunication.getBuilding());

            labelArr = new Label[jsonArray.size()];
            databaseArr = new Label[jsonArray.size()];

            for (int i = 0; i < labelArr.length; i++) {
                Label label = new Label();
                label.getStyleClass().add("buildingName");

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

        gp.getChildren().clear();
        gp.getRowConstraints().clear();

        int size = labelArr.length;
        double rowHeight = 200.0;
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
            AnchorPane pane = new AnchorPane();
            pane.getStyleClass().add("buildingBlock");
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(340, 200);
            Label expanded = new Label("");
            expanded.setVisible(false);
            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String status = expanded.getText();

                    for (Node node : gp.getChildren()) {
                        Label expanded = (Label) ((AnchorPane) node).getChildren().get(0);
                        expanded.setText("false");
                    }
                    if (status.equals("false")) {
                        expanded.setText("true");

                        infoPane.setLayoutX(pane.getLayoutX() + 88);
                        double offset = ((ScrollPane) scene.lookup("#buildings")).vvalueProperty()
                                // multiplied by (scrollableAreaHeight - visibleViewportHeight)
                                .multiply(
                                        gp.heightProperty()
                                                .subtract(
                                                        ((ScrollPane) scene.lookup("#buildings")).getViewportBounds().getHeight())).getValue();

                        //System.out.println(vOffset);
                        infoPaneY = pane.getLayoutY();
                        infoPane.setLayoutY(pane.getLayoutY() - offset + 308 + 200);
                        infoPane.setVisible(true);
                        //System.out.println("x: " + pane.getLayoutX() + "; y: " + pane.getLayoutY());

                        addInfo(pane.getChildren().get(1).getId());
                    } else {
                        infoPane.setVisible(false);
                    }
                }
            });
            addinfoPaneHListener();

            pane.getChildren().add(expanded);

            Label buildingId = new Label(labelArr[i].getText().split("\n")[0]);
            buildingId.setId(labelArr[i].getId());
            buildingId.getStyleClass().add("buildingId");
            Label buildingName = new Label(labelArr[i].getText().split("\n")[1]);
            buildingName.setLayoutY(40);
            buildingName.getStyleClass().add("buildingName");
            buildingName.setWrapText(true);
            buildingLabelListener(buildingName);

            pane.getChildren().add(buildingId);
            pane.getChildren().add(buildingName);

            gp.add(pane, i % 3, row);
        }
    }

    /**
     * Adds all necessary listeners for the infoPane to "stay in place".
     */
    private static void addinfoPaneHListener() {
        ((ScrollPane) scene.lookup("#buildings")).widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for (int i = 0; i < gp.getChildren().size(); i++) {
                    if (((Label) ((AnchorPane) gp.getChildren().get(i)).getChildren().get(0)).getText().equals("true")) {
                        infoPane.setLayoutX(gp.getChildren().get(i).getLayoutX() + 88);
                    }
                }
            }
        });
        ((ScrollPane) scene.lookup("#buildings")).heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double offset = ((ScrollPane) scene.lookup("#buildings")).vvalueProperty()
                        // multiplied by (scrollableAreaHeight - visibleViewportHeight)
                        .multiply(
                                gp.heightProperty()
                                        .subtract(
                                                ((ScrollPane) scene.lookup("#buildings")).getViewportBounds().getHeight())).getValue();

                infoPane.setLayoutY(infoPaneY - offset + 308 + 200);
            }
        });
        ((ScrollPane) scene.lookup("#buildings")).vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double offset = ((ScrollPane) scene.lookup("#buildings")).vvalueProperty()
                        // multiplied by (scrollableAreaHeight - visibleViewportHeight)
                        .multiply(
                                gp.heightProperty()
                                        .subtract(
                                                ((ScrollPane) scene.lookup("#buildings")).getViewportBounds().getHeight())).getValue();

                infoPane.setLayoutY(infoPaneY - offset + 308 + 200);
            }
        });
    }

    /**
     * Adds the info of the building.
     * @param id the id of the building
     */
    private static void addInfo(String id) {
        while (infoPane.getChildren().size() > 3) {
            infoPane.getChildren().remove(3);
        }

        JSONParser json = new JSONParser();

        try {
            JSONObject opening = (JSONObject) json.parse((String) BuildingMap.get(Long.parseLong(id)).get("opening_hours"));
            Label openingHours = new Label("Monday:\t\t" + opening.get("mo") + "\n"
                    + "Tuesday:\t\t" + opening.get("tu") + "\n"
                    + "Wednesday:\t" + opening.get("we") + "\n"
                    + "Thursday:\t\t" + opening.get("th") + "\n"
                    + "Friday:\t\t" + opening.get("fr") + "\n"
                    + "Saturday:\t\t" + opening.get("sa") + "\n"
                    + "Sunday:\t\t" + opening.get("su"));
            openingHours.setStyle("-fx-font-family: 'Helvetica', Arial, sans-serif; -fx-text-fill: silver; -fx-font-size: 15;");
            openingHours.setLayoutY(20);
            openingHours.setLayoutX(20);
            infoPane.getChildren().add(openingHours);

            Label address = new Label();
            address.setTextFill(Color.SILVER);
            address.setText((String) BuildingMap.get(Long.parseLong(id)).get("address"));
            address.setLayoutY(172);
            address.setLayoutX(20);
            address.setStyle("-fx-font-family: 'Helvetica', Arial, sans-serif; -fx-text-fill: silver; -fx-font-size: 15;");
            infoPane.getChildren().add(address);

            ScrollPane sp = new ScrollPane();
            sp.setFitToWidth(true);
            sp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
            AnchorPane.setRightAnchor(sp, 0.0);
            AnchorPane.setLeftAnchor(sp, 0.0);
            AnchorPane.setBottomAnchor(sp, 0.0);
            AnchorPane.setTopAnchor(sp, 230.0);
            GridPane gp = new GridPane();
            gp.setStyle("-fx-background-color: transparent;");
            gp.setVgap(10);
            gp.setPadding(new Insets(10));
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100);
            gp.getColumnConstraints().add(cc);
            sp.setContent(gp);
            infoPane.getChildren().add(sp);

            JSONArray rooms = (JSONArray) json.parse(ServerCommunication.getRoomsFromBuilding(id));
            for (Object object : rooms) {
                RowConstraints rc = new RowConstraints();
                rc.setPrefHeight(20);
                gp.getRowConstraints().add(rc);

                JSONObject obj = (JSONObject) object;
                Label label = new Label((String) obj.get("room_name"));
                label.setFont(new Font("Arial", 15));
                label.setTextFill(Color.LIGHTGRAY);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                gp.addRow(gp.getRowCount() - 1, label);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * This adds a listener to the pane width and sets the label to the width of the grid.
     * @param label the label to change.
     */
    private static void buildingLabelListener(Label label) {
        gp.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //width of a tile = (newValue/3) - 40 (for the hgap)
                double tileWidth = ((double) newValue / 3) - 40;
                label.setPrefWidth(tileWidth);
            }
        });
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
