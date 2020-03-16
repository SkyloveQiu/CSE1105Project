package nl.tudelft.oopp.group43.project.controllers;

import java.util.*;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoomController {


    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @GetMapping("/room")
    @ResponseBody
    public List<Room> getRoom() {
        return roomRepository.findAll();
    }

    // {"building":{"building_number":32,"building_name":"EWI","address":"MEK","opening_hours":"34"},
    // "room_name":"32","attributes":"32","id":3}
    //or {"building":{"building_number":33},"room_name":"32","attributes":"32","id":3}
    @PostMapping("/room")
    @ResponseBody
    public String createRoom(@RequestBody Room newRoom) {
        roomRepository.save(newRoom);

        Thread update_data = new Thread(new RoomAttributesUpdater());

        if (!RoomAttributesUpdater.isRunning()) {
            update_data.start();
        }


        return "NEW ROOM: " + newRoom.getRoomName();
    }

    /**
     * get the building.
     *
     * @param buildingNumber the number of building you want to get.
     * @return the result.
     */
    @GetMapping("/room/{buildingNumber}")
    @ResponseBody
    public List<Room> getRoomByBuildingNumber(@PathVariable int buildingNumber) {
        Building result = buildingRepository.findBuildingByBuildingNumber(buildingNumber);
        Set<Room> roomSet = result.getRooms();

        System.out.println(roomSet.isEmpty());
        Iterator<Room> roomIterator = roomSet.iterator();
        List<Room> roomList = new ArrayList<Room>();
        while (roomIterator.hasNext()) {
            roomList.add(roomIterator.next());
            System.out.println(roomList.get(0));
        }
        return roomList;
    }

    /**
     * @param blinds            if there is a blind
     * @param desktop           if there is a desktop
     * @param projector         if there is an projector
     * @param chalkBoard        if there is a chalkBoard
     * @param microphone        if there is a microphone
     * @param smartBoard        if there is a smartBoard
     * @param whiteBoard        if there is a whiteBoard
     * @param powerSupply       if there is a powerSupply
     * @param soundInstallation if there is a soundInstallation
     * @param wheelChair        if there is a wheelChair
     * @param minSpace          the min space
     * @return return the result based on the attributes
     * @throws ParseException
     */
    @GetMapping("/filter")
    @ResponseBody
    public List<Room> getRoomByBuildingNumber(@RequestParam(value = "blinds", defaultValue = "false") boolean blinds,
                                              @RequestParam(value = "desktop", defaultValue = "false") boolean desktop,
                                              @RequestParam(value = "projector", defaultValue = "false") boolean projector,
                                              @RequestParam(value = "chalkBoard", defaultValue = "false") boolean chalkBoard,
                                              @RequestParam(value = "microphone", defaultValue = "false") boolean microphone,
                                              @RequestParam(value = "smartBoard", defaultValue = "false") boolean smartBoard,
                                              @RequestParam(value = "whiteBoard", defaultValue = "false") boolean whiteBoard,
                                              @RequestParam(value = "powerSupply", defaultValue = "false") boolean powerSupply,
                                              @RequestParam(value = "soundInstallation", defaultValue = "false") boolean soundInstallation,
                                              @RequestParam(value = "wheelChair", defaultValue = "false") boolean wheelChair,
                                              @RequestParam(value = "minSpace", defaultValue = "0") int minSpace) throws ParseException {

        if (!RoomAttributesUpdater.isRunning()) {

            List<Room> result = new ArrayList<>();

            List<Integer> validRooms = RoomAttributesUpdater.list_higher_than(minSpace);

            List<Room>[] RoomList = RoomAttributesUpdater.getRoomList().getBuckets();

            JSONParser parser = new JSONParser();
            JSONObject object;


            for (Integer i : validRooms) {


                for (Room room : RoomList[i]) {
                    object = (JSONObject) parser.parse(room.getAttributes());


                    if (Boolean.parseBoolean(object.get("blinds").toString()) == blinds &&
                        Boolean.parseBoolean(object.get("desktopPc").toString()) == desktop &&
                        Boolean.parseBoolean(object.get("projector").toString()) == projector &&
                        Boolean.parseBoolean(object.get("chalkBoard").toString()) == chalkBoard &&
                        Boolean.parseBoolean(object.get("microphone").toString()) == microphone &&
                        Boolean.parseBoolean(object.get("smartBoard").toString()) == smartBoard &&
                        Boolean.parseBoolean(object.get("whiteBoard").toString()) == (whiteBoard) &&
                        Boolean.parseBoolean(object.get("powerSupply").toString()) == (powerSupply) &&
                        Boolean.parseBoolean(object.get("soundInstallation").toString()) == soundInstallation &&
                        Boolean.parseBoolean(object.get("wheelChairAccessible").toString()) == wheelChair) {
                        result.add(roomRepository.getRoomById(room.getId()));
                    }
                }
            }
            return result;
        } else {
            return new ArrayList<>();
        }


    }

    @DeleteMapping("room/{roomId}")
    @ResponseBody
    public void removeRoom(@PathVariable int roomId) {
        roomRepository.deleteById(roomId);

        Thread update_data = new Thread(new RoomAttributesUpdater());
        if (!RoomAttributesUpdater.isRunning()) {
            update_data.start();
        }
    }


}
