package nl.tudelft.oopp.group43.project.payload;


import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoomAttributesUpdater implements Runnable {

    private static List<Room> rooms = new ArrayList<>();
    private static boolean run = false;
    private static List<Integer> roomSeats = new ArrayList<>();
    private static  BucketsList roomList;

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
        }
    }

    public void update() throws ParseException {
        run = true;



       rooms = roomRepository.findAll();

        for (Room room : rooms) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(room.getAttributes());
            Integer value = Integer.valueOf(object.get("seatCapacity").toString());
            if (!roomSeats.contains(value)) {
                roomSeats.add(value);
            }


        }

        roomList =  new BucketsList();

        for (Room room : rooms) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(room.getAttributes());
            Integer value = Integer.valueOf(object.get("seatCapacity").toString());

            roomList.add(value,room);




        }



        run=false;

    }

    public static List<Integer> list_higher_than(int number) {

        List<Integer> list = new ArrayList<>();

        for (Integer i : roomSeats)
            if (i >= number) {
                list.add(i);
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

    public static class BucketsList{

        private LinkedList<Room>[] buckets;

        /**
         * Constructs a new BucketList for rooms.
         */
        public BucketsList() {
            this.buckets = new LinkedList[Collections.max(roomSeats)+1];
            for(int i=0; i<buckets.length;i++)
                buckets[i] = new LinkedList<>();
        }

        public void add(int index,Room room){
            buckets[index].add(room);
        }

        public List[] getBuckets() {
            return buckets;
        }
    }
}
