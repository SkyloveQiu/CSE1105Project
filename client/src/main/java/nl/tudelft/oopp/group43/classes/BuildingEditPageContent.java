package nl.tudelft.oopp.group43.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BuildingEditPageContent extends BuildDataScene implements Runnable {


    /**
     * Constructor for the BuildDataScene.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildingEditPageContent(Stage stage, ThreadLock lock) {
        super(stage, lock);
    }

    /**
     * Construct the list for the EditBuildingPage.
     */
    @Override
    public void run() {

        synchronized (this.getLock()) {
            try {
                while (this.getLock().flag == 0) {
                    getLock().wait();
                }

                Label[] labelArr = BuildingsConfig.getLabel();
                addBuildingList(labelArr);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Adds the buildings to the accordion of the Edit page.
     *
     * @param labelArr The array containing all building labels.
     */
    private void addBuildingList(Label[] labelArr) {
        final ScrollPane sp = (ScrollPane) this.getScene().lookup("#building_list");
        Label buildingNumber = (Label) this.getStage().getScene().lookup("#buildingNumber");
        TextField buildingName = (TextField) this.getStage().getScene().lookup("#buildingName");
        TextField buildingAddress = (TextField) this.getStage().getScene().lookup("#buildingAddress");
        Label editMsg = (Label) this.getStage().getScene().lookup("#editMsg");
        Pane pane = new Pane();
        double pos = 0.0;
        String text = "Are you sure that you want to edit ";


        for (int i = 0; i < labelArr.length; i++) {
            Label label = new Label();


            label.setText(labelArr[i].getText().replaceAll("\\n", " "));

            JSONObject obj = BuildingsConfig.getBuilding(i);


            // handles the building labels being clicked + highlighting the selected building
            EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    for (Node n : pane.getChildren()) {
                        n.setStyle("-fx-border-color: black; -fx-border-width: 3;");
                    }
                    label.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
                    editMsg.setText(text + label.getText() + " building?");
                    buildingNumber.setText(((Long) obj.get("building_number")).toString());
                    buildingAddress.setText((String) obj.get("address"));
                    buildingName.setText((String) obj.get("building_name"));


                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject hoursObj = (JSONObject) parser.parse(((String) obj.get("opening_hours")).replaceAll("\\\\", ""));
                        putHours("monday", (String) hoursObj.get("mo"));
                        putHours("tuesday", (String) hoursObj.get("tu"));
                        putHours("wednesday", (String) hoursObj.get("we"));
                        putHours("thursday", (String) hoursObj.get("th"));
                        putHours("friday", (String) hoursObj.get("fr"));
                        putHours("saturday", (String) hoursObj.get("sa"));
                        putHours("sunday", (String) hoursObj.get("su"));

                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                }

            };


            label.addEventFilter(MouseEvent.MOUSE_CLICKED, event);
            label.setMaxWidth(Integer.MAX_VALUE);
            label.setPrefWidth(500);
            label.setMinWidth(0);
            label.setMinHeight(30.0);
            label.setStyle("-fx-background-color: #daebeb");
            label.setStyle("-fx-border-color: black; -fx-border-width: 3;");
            label.setLayoutY(pos);

            pane.getChildren().add(label);

            pos += 35.0;
        }
        pane.setMinHeight(pos);
        sp.setLayoutY(40.0);
        sp.setLayoutX(40.0);
        sp.setMinSize(150.0, 250.0);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sp.setContent(pane);
            }
        });
    }

    /**
     * Puts the information regarding the opening hours of a specific day of the week.
     *
     * @param day  - represents the day of a week
     * @param data - data regarding the opening hours of that day.
     */
    private void putHours(String day, String data) {
        ChoiceBox<String> openH = (ChoiceBox<String>) this.getStage().getScene().lookup("#" + day + "OpenH");
        ChoiceBox<String> openM = (ChoiceBox<String>) this.getStage().getScene().lookup("#" + day + "OpenM");
        ChoiceBox<String> closeH = (ChoiceBox<String>) this.getStage().getScene().lookup("#" + day + "CloseH");
        ChoiceBox<String> closeM = (ChoiceBox<String>) this.getStage().getScene().lookup("#" + day + "CloseM");
        String time = "--";
        if (data.equals("closed")) {
            openH.setValue(time);
            openM.setValue(time);
            closeH.setValue(time);
            closeM.setValue(time);
            return;
        }
        time = data.substring(0, 2);
        openH.setValue(time);
        time = data.substring(3, 5);
        openM.setValue(time);
        time = data.substring(6, 8);
        closeH.setValue(time);
        time = data.substring(9, 11);
        closeM.setValue(time);

    }
}
