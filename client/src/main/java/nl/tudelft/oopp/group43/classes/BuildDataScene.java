package nl.tudelft.oopp.group43.classes;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BuildDataScene implements Runnable {

    private Stage stage;
    private Scene scene;
    private ThreadLock lock;

    /**
     * Constructor for the BuildDataScene.
     *
     * @param stage - the actual stage.
     * @param lock  - to synchronize threads.
     */
    public BuildDataScene(Stage stage, ThreadLock lock) {
        this.stage = stage;
        scene = stage.getScene();
        this.lock = lock;
    }


    /**
     * Returns the actual Scene.
     * @return actual Scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Returns the actual Stage.
     * @return actual Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns the value of the Thread lock.
     * @return value of the Thread lock
     */
    public ThreadLock getLock() {
        return lock;
    }
}
