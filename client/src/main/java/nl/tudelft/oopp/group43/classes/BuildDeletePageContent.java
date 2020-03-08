package nl.tudelft.oopp.group43.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class BuildDeletePageContent extends BuildDataScene implements Runnable {


    /**
     * Constructor for the BuildDeletePageContent.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildDeletePageContent(Stage stage, ThreadLock lock) {
        super(stage, lock);
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
                Label[] labelArr = BuildingsConfig.getLabel();
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
        final ScrollPane sp = (ScrollPane) this.getScene().lookup("#building_list");
        Pane pane = new Pane();
        double pos = 0.0;
        String text = "Are you sure that you want to delete ";


        for (int i = 0; i < labelArr.length; i++) {
            Label label = new Label();

            label.setText(labelArr[i].getText().replaceAll("\\n", " "));

            // handles the building labels being clicked + highlighting the selected building
            EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    msg.setText(text + label.getText() + " building?");
                    for (Node n : pane.getChildren()) {
                        n.setStyle("-fx-border-color: black; -fx-border-width: 3;");
                    }
                    label.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
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


}
