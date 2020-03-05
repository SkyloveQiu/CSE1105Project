package nl.tudelft.oopp.group43.classes;

import javafx.scene.control.Label;

import nl.tudelft.oopp.group43.communication.ServerCommunication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BuildingData implements Runnable {

    private ThreadLock lock;

    /**
     * Constructor for the BuildingData.
     * @param lock - to synchronize threads.
     */
    public BuildingData(ThreadLock lock) {
        this.lock = lock;
    }


    /**
     * Takes the buildings and notify all other Threads that it finished to read the buildings.
     */
    @Override
    public void run() {
        synchronized (lock) {
            try {
                getBuildings();
                lock.flag = 1;
                lock.notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Takes buildings from the server and store them in an array of labels.
     * @throws ParseException - if something goes wrong with the JSON Parser.
     */
    public void getBuildings() throws ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(ServerCommunication.getBuilding());
        Label[] labels = new Label[jsonArray.size()];

        for (int i = 0; i < labels.length; i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            Label label = new Label();
            label.setPrefWidth(Integer.MAX_VALUE);
            label.setMaxWidth(Integer.MAX_VALUE);
            label.setMinWidth(0);
            label.setStyle("-fx-background-color: #daebeb");
            label.setText("Building " + obj.get("building_number") + ":\n" + obj.get("building_name"));
            label.setId(Long.toString((Long) obj.get("building_number")));
            labels[i] = label;
        }

        BuildingsConfig.setLabel(labels);

    }


}
