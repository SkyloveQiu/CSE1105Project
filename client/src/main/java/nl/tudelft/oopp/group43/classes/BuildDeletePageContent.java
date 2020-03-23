package nl.tudelft.oopp.group43.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.content.BuildingPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BuildDeletePageContent extends BuildDataScene implements Runnable {

    private Scene scene;

    /**
     * Constructor for the BuildDeletePageContent.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildDeletePageContent(Stage stage, ThreadLock lock) {
        super(stage, lock);
        this.scene = stage.getScene();
    }

    /**
     * Construct the list for the DeleteBuildingPage.
     */
    @Override
    public void run() {

        synchronized (this.getLock()) {
            try {
                while (this.getLock().flag == 0) {
                    getLock().wait();
                }

                Label msg = (Label) this.getStage().getScene().lookup("#deleteMsg");
                Label[] labelArr = BuildingPageContent.getLabelArr();
                addBuildingList(labelArr, msg);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Adds the buildings to the accordion of the  Delete page.
     *
     * @param labelArr The array containing all building labels.
     * @param msg      The msg for the warning message.
     */
    private void addBuildingList(Label[] labelArr, Label msg) {
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
            addEvent(label, msg, (long) obj.get("building_number"));

            ap.getChildren().add(label);
        }

        ScrollPane sp = (ScrollPane) scene.lookup("#deleteBuildingList");
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
     * @param msg message label to notify the user
     */
    private void addEvent(Label label, Label msg, long newId) {
        Label id = (Label) scene.lookup("#deleteId");
        label.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                id.setText(Long.toString(newId));
                msg.setText("Are you sure you want to delete " + label.getText());
            }
        });
    }


}
