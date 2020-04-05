package nl.tudelft.oopp.group43.project.payload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class RoomAttributesUpdater implements Runnable {

    private static List<Room> rooms = new ArrayList<>();
    private static boolean run = false;
    private static List<Integer> roomSeats;
    private static BucketsList roomList;
    private static Hashtable tableRooms;

    @Autowired
    private RoomRepository roomRepository;

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent ready) throws ParseException {
        update();
    }

    @Override
    public void run() {
        try {
            update();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the data from the server regarding the rooms.
     *
     * @throws ParseException throws an error if the object is invalid
     */
    public void update() throws ParseException {
        run = true;


        rooms = roomRepository.findAll();
        roomSeats = new ArrayList<>();
        tableRooms = new Hashtable<Integer, Room>();

        for (Room room : rooms) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(room.getAttributes());
            Integer value = Integer.valueOf(object.get("seatCapacity").toString());
            if (!roomSeats.contains(value)) {
                roomSeats.add(value);
            }


        }

        roomList = new BucketsList();

        for (Room room : rooms) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(room.getAttributes());
            Integer value = Integer.valueOf(object.get("seatCapacity").toString());

            roomList.add(value, room);


        }

        for (Room room : rooms) {
            tableRooms.put(room.getId(), room);
        }


        run = false;

    }

    /**
     * Returns the valid seats.
     *
     * @param number the number of seats
     * @return a list with the "valid seats"
     */
    public static List<Integer> list_higher_than(int number) {

        List<Integer> list = new ArrayList<>();

        for (Integer i : roomSeats) {
            if (i >= number) {
                list.add(i);
            }
        }

        return list;
    }


    public static List<Room> getRooms() {
        return rooms;
    }

    public static boolean isRunning() {
        return run;
    }

    public static List<Integer> getRoomSeats() {
        return roomSeats;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public static BucketsList getRoomList() {
        return roomList;
    }

    public static Hashtable getTableRooms() {
        return tableRooms;
    }

    public static class BucketsList {

        private LinkedList<Room>[] buckets;

        /**
         * Constructs a new BucketList for rooms.
         */
        public BucketsList() {
            this.buckets = new LinkedList[Collections.max(roomSeats) + 1];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new LinkedList<>();
            }
        }

        public void add(int index, Room room) {
            buckets[index].add(room);
        }

        public List[] getBuckets() {
            return buckets;
        }
    }

    public static boolean verify(Room r) {
        return (!r.getRoomName().equals("deleted"));
    }


}
