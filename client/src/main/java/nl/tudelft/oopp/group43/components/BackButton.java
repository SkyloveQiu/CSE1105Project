package nl.tudelft.oopp.group43.components;

import java.io.IOException;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import nl.tudelft.oopp.group43.views.LoginDisplay;
import nl.tudelft.oopp.group43.views.MainPageDisplay;
import nl.tudelft.oopp.group43.views.RegisterDisplay;
import nl.tudelft.oopp.group43.views.RoomPageDisplay;

public class BackButton {

    /*
    Strings corresponding to sceneStack get pushed when te window moves to a new scene.
    For the Room Page also the building ID gets pushed in order of:

    push(buildingID);
    push("room");
     */
    private static Stack<String> sceneStack = new Stack<String>();
    private Button backButton;

    /**
     * Constructor for the back button, creates an instance of the back button that can be added to scenes.
     */
    public BackButton() {
        backButton = new Button();
        backButton.setLayoutX(51.0);
        backButton.setPrefHeight(30.0);
        backButton.setText("Back");
        backButton.setTextAlignment(TextAlignment.CENTER);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    backPressed((Stage) ((Node) event.getSource()).getScene().getWindow());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        backButton.setOnAction(event);
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

            // When a new scene gets loaded first thing done is the scene name gets pushed, but when a back button is pressed
            // the current scene name is still there so that needs to be deleted.
            // And then there needs to be a new isEmpty check because it can happen that there is no previous scene.
            if (!sceneStack.isEmpty()) {
                String scene = sceneStack.pop();
                switch (scene) {
                    case "main":
                        MainPageDisplay md = new MainPageDisplay();
                        md.start(stage);
                        break;
                    case "room":
                        RoomPageDisplay rd = new RoomPageDisplay();
                        rd.start(stage, sceneStack.pop());
                        break;
                    case "login":
                        LoginDisplay ld = new LoginDisplay();
                        ld.start(stage);
                        break;
                    case "register":
                        RegisterDisplay rgd = new RegisterDisplay();
                        rgd.start(stage);
                        break;
                        //case "reservation":
                    default:
                        break;
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
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Sets the back button of the class.
     *
     * @param button the button to set
     */
    public void setBackButton(Button button) {
        backButton = button;
    }

}
