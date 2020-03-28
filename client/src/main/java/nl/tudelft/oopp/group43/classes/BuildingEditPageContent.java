package nl.tudelft.oopp.group43.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BuildingEditPageContent extends BuildDataScene implements Runnable {

    private Scene scene;

    /**
     * Constructor for the BuildDataScene.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildingEditPageContent(Stage stage, ThreadLock lock) {
        super(stage, lock);
        this.scene = stage.getScene();
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

                Label[] labelArr = BuildingPageContent.getLabelArr();
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
        AnchorPane ap = new AnchorPane();
        ap.setMinHeight(30.0 * labelArr.length);
        JSONArray array = BuildingPageContent.getJsonArray();

        for (int i = 0; i < labelArr.length; i++) {
            String buildingName = labelArr[i].getText().replaceAll("\\n", " ");
            Label label = new Label(buildingName);
            label.setPrefHeight(30);
            label.getStyleClass().add("buildingLabel");
            AnchorPane.setRightAnchor(label,5.0);
            AnchorPane.setLeftAnchor(label,5.0);
            AnchorPane.setTopAnchor(label, i * 30.0);

            JSONObject obj = (JSONObject) array.get(i);
            addEvent(label,(Long) obj.get("building_number"), obj);

            ap.getChildren().add(label);
        }

        ScrollPane sp = (ScrollPane) scene.lookup("#editBuildingList");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sp.setContent(ap);
            }
        });
    }

    /**
     * Adds an event to when the label is clicked.
     * @param label label of the event
     */
    private void addEvent(Label label, long id, JSONObject obj) {
        Label editId = (Label) scene.lookup("#editId");

        label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editId.setText(Long.toString(id));

                Label number = (Label) scene.lookup("#editBuildingNumber");
                number.setText("Building being edited: " + id);

                TextField name = (TextField) scene.lookup("#editBuildingName");
                name.setText((String) obj.get("building_name"));

                TextField address = (TextField) scene.lookup("#editBuildingAddress");
                address.setText((String) obj.get("address"));

                JSONParser json = new JSONParser();
                JSONObject oh = null;
                try {
                    oh = (JSONObject) json.parse((String) obj.get("opening_hours"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextField monday = (TextField) scene.lookup("#editMondayHours");
                monday.setText((String) oh.get("mo"));

                TextField tuesday = (TextField) scene.lookup("#editTuesdayHours");
                tuesday.setText((String) oh.get("tu"));

                TextField wednesday = (TextField) scene.lookup("#editWednesdayHours");
                wednesday.setText((String) oh.get("we"));

                TextField thursday = (TextField) scene.lookup("#editThursdayHours");
                thursday.setText((String) oh.get("th"));

                TextField friday = (TextField) scene.lookup("#editFridayHours");
                friday.setText((String) oh.get("fr"));

                TextField saturday = (TextField) scene.lookup("#editSaturdayHours");
                saturday.setText((String) oh.get("sa"));

                TextField sunday = (TextField) scene.lookup("#editSundayHours");
                sunday.setText((String) oh.get("su"));
            }
        });
    }

}
