package nl.tudelft.oopp.group43.classes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class BuildDeletePageContent implements Runnable {

    private Stage stage;
    private Scene scene;
    private ThreadLock lock;

    /**
     * Constructor for the BuildDeletePageContent.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildDeletePageContent(Stage stage, ThreadLock lock) {
        this.stage = stage;
        scene = stage.getScene();
        this.lock = lock;
    }

    /**
     * Construct the accordion for the DeleteBuildingPage.
     */
    @Override
    public void run() {

        synchronized (lock) {
            try {
                while (lock.flag == 0) {
                    lock.wait();
                }

                Accordion accordion = (Accordion) stage.getScene().lookup("#building_list");
                Label msg = (Label) stage.getScene().lookup("#deleteMsg");
                Label[] labelArr = BuildingsConfig.getLabel();
                addBuildingList(labelArr, accordion, stage, msg);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Adds the buildings to the accordion of the  Delete page.
     *
     * @param labelArr The array containing all building labels.
     * @param acc      The Accordion where to add the buildings.
     * @param stage    Stage of the window.
     * @param msg      The msg for the warning message.
     */
    private void addBuildingList(Label[] labelArr, Accordion acc, Stage stage, Label msg) {
        final TitledPane tp = acc.getPanes().get(0);
        Pane pane = new Pane();
        double pos = 0.0;
        String text = "Are you sure that you want to delete ";


        for (int i = 0; i < labelArr.length; i++) {
            Label label = new Label();


            label.setText(labelArr[i].getText().replaceAll("\\n", " "));

            EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    msg.setText(text + label.getText() + "building?");
                }
            };


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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tp.setContent(sp);
            }
        });


        BuildingsConfig.setAccordionHeight(tp.getMinHeight());
    }


}
