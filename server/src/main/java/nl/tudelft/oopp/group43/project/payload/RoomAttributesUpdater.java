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

    private static Hashtable<Integer, Room> rooms = new Hashtable<>();
    private static boolean run = false;
    private static List<Integer> room_seats = new ArrayList<>();

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

        List<Room> room_list = roomRepository.findAll();

        for (Room room : room_list) {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(room.getAttributes());

            Integer value = Integer.valueOf(object.get("seatCapacity").toString());

            rooms.put(value, room);

            if (!room_seats.contains(value)) {
                room_seats.add(value);
            }


        }

    }

    public static List<Integer> list_higher_than(int number) {

        List<Integer> list = new ArrayList<>();

        for (Integer i : room_seats)
            if (i >= number) {
                list.add(i);
            }

        return list;
    }

    public static Hashtable<Integer, Room> getRooms() {
        return rooms;
    }

    public static boolean isRunning() {
        return run;
    }

    public static List<Integer> getRoom_seats() {
        return room_seats;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }
}
