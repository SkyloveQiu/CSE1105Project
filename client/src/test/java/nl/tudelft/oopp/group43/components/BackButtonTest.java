package nl.tudelft.oopp.group43.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.control.Button;
import org.junit.Test;

public class BackButtonTest {

    @Test
    public void backButtonConstructorTest() {
        BackButton btn = new BackButton();
        assertNotNull(btn.getBackButton());
    }

    @Test
    public void setBackButtonTest() {
        BackButton btn = new BackButton();
        Button button = new Button();
        btn.setBackButton(button);
        assertEquals(button, btn.getBackButton());
    }

    @Test
    public void sceneStackTest() {
        BackButton.pushScene("test");
        assertEquals("test", BackButton.popScene());
    }

}
