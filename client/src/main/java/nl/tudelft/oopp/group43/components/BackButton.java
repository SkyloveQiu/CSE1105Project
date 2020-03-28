package nl.tudelft.oopp.group43.components;

import java.io.IOException;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.communication.ServerCommunication;
import nl.tudelft.oopp.group43.sceneloader.SceneLoader;

public class BackButton {

    /*
    Strings corresponding to sceneStack get pushed when te window moves to a new scene.
    For the Room Page also the building ID gets pushed in order of:

    push(buildingID);
    push("room");

    For the Reservation Page also the roomID with the buildingID like before, but in the format of:
    buildingID;roomID
     */
    private static Stack<String> sceneStack = new Stack<String>();
    private ImageView backButton;

    /**
     * Constructor for the back button, creates an instance of the back button that can be added to scenes.
     */
    public BackButton(ImageView backButton) {
        this.backButton = backButton;
        backButton.setVisible(!sceneStack.isEmpty());
        if (sceneStack.size() > 1) {
            backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        backPressed((Stage) ((Node) event.getSource()).getScene().getWindow());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            backButton.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ((Node) event.getSource()).getScene().setCursor(Cursor.HAND);
                }
            });

            backButton.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ((Node) event.getSource()).getScene().setCursor(Cursor.DEFAULT);
                }
            });
        } else {
            backButton.setVisible(false);
        }
    }

    /**
     * handles the back button being pressed.
     *
     * @param stage Window of the application on which we change the scene
     * @throws IOException Throws an IOException when there is a problem loading the scene
     */
    private static void backPressed(Stage stage) throws IOException {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop();
            if (!sceneStack.isEmpty()) {
                String scene = sceneStack.pop();
                while (!sceneStack.isEmpty() && !ServerCommunication.getToken().equals("invalid") && scene.equals("login")) {
                    scene = sceneStack.pop();
                }
                if (sceneStack.isEmpty()) {
                    ImageView btn = (ImageView) stage.getScene().lookup("#back_arrow");
                    btn.setVisible(false);
                } else {
                    SceneLoader.setScene(scene);
                    SceneLoader sl = new SceneLoader();
                    sl.start(stage);
                }
            }
        }
    }

    /**
     * Method to push scenes to the stack.
     *
     * @param string Scene gets pushed as string with the room page first pushing the buildingID
     */
    public static void pushScene(String string) {
        sceneStack.push(string);
    }

    /**
     * Pops a (String) scene from the stack.
     *
     * @return A String with in it the corresponding scene name
     */
    public static String popScene() {
        return sceneStack.pop();
    }

    /**
     * Gets the back button of the class.
     *
     * @return The back button
     */
    public ImageView getBackButton() {
        return backButton;
    }

    /**
     * Sets the back button of the class.
     *
     * @param button the button to set
     */
    public void setBackButton(ImageView button) {
        backButton = button;
    }

}
