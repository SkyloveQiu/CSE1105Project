package nl.tudelft.oopp.group43.components;

import java.io.IOException;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.classes.ThreadLock;
import nl.tudelft.oopp.group43.content.CalendarPageContent;
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
    private boolean isCalendar = false;

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
            backButton.setImage(null);
        }
    }

    /**
     * Constructor for the back button, creates an instance of the back button that can be added to scenes.
     * This constructor is specially for the calendar page, when clicked on the backbutton a alert gets shown asking for the user to wait until all entries in the calendar are saved.
     */
    public BackButton(ImageView backButton, String calendar) {
        isCalendar = true;

        this.backButton = backButton;
        backButton.setVisible(!sceneStack.isEmpty());
        if (sceneStack.size() > 1) {
            backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        // Checks if the current scene is the calendar, and if any entries have been added that need to be saved.
                        if (isCalendar && CalendarPageContent.areEntriesAdded()) {
                            CalendarPageContent.saveEntries();
                            while (ThreadLock.flag != 0) {
                                Alert waitForSave = new Alert(Alert.AlertType.WARNING);
                                waitForSave.setContentText("Please wait for the calendar to save all entries of the calendar!");
                                waitForSave.showAndWait();
                            }
                            backPressed((Stage) ((Node) event.getSource()).getScene().getWindow());
                        } else {
                            backPressed((Stage) ((Node) event.getSource()).getScene().getWindow());
                        }
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
            backButton.setImage(null);
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
                SceneLoader.setScene("scene");
                SceneLoader sl = new SceneLoader();
                sl.start(stage);
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
