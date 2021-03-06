package nl.tudelft.oopp.group43.content;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class RegisterPageContent {

    /**
     * Adds the RadioButtons to a ToggleGroup so they get deselected when another one is selected.
     *
     * @param scene The current scene
     */
    public static void addContent(Scene scene) {
        ToggleGroup radioButtons = new ToggleGroup();
        RadioButton studentbtn = (RadioButton) scene.lookup("#student");
        RadioButton employeebtn = (RadioButton) scene.lookup("#employee");
        RadioButton otherbtn = (RadioButton) scene.lookup("#other");
        studentbtn.setToggleGroup(radioButtons);
        employeebtn.setToggleGroup(radioButtons);
        otherbtn.setToggleGroup(radioButtons);
    }

}
