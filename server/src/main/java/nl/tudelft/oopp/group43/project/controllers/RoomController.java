package nl.tudelft.oopp.group43.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;

import nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


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

    /**
     * Get new rooms.
     *
     * @param newRoom the new room that comes
     * @return a response
     */
    @PostMapping("/room")
    @ResponseBody
    public String createRoom(@RequestBody Room newRoom) {
        roomRepository.save(newRoom);

        Thread updateData = new Thread(new RoomAttributesUpdater());

        if (!RoomAttributesUpdater.isRunning()) {
            updateData.start();
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
     * Gets a filtered rooms.
     *
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
     * @throws ParseException throws an error when the object is not valid
     */
    @GetMapping("/filter")
    @ResponseBody
    public List<Room> getFilteredRooms(@RequestParam(value = "blinds", defaultValue = "false") boolean blinds,
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

        final String[] filteredAttributes = {"blinds",
            "desktopPc",
            "projector",
            "chalkBoard",
            "microphone",
            "smartBoard",
            "whiteBoard",
            "powerSupply",
            "soundInstallation",
            "wheelChairAccessible"};


        if (!RoomAttributesUpdater.isRunning()) {

            List<Room> result = new ArrayList<>();

            List<Integer> validRooms = RoomAttributesUpdater.list_higher_than(minSpace);

            List<Room>[] roomListData = RoomAttributesUpdater.getRoomList().getBuckets();

            List<Room> rooms = RoomAttributesUpdater.getRooms();

            JSONParser parser = new JSONParser();
            JSONObject object;


            for (Integer i : validRooms) {


                for (Room room : roomListData[i]) {
                    result.add(rooms.get(room.getId() - 1));

                }
            }
            //{\"blinds\": true, \"display\": false, \"desktopPc\": true,
            // \"projector\": true, \"spaceType\": \"Instruction hall\", \"chalkBoard\": true,
            // \"microphone\": false, \"smartBoard\": false, \"whiteBoard\": false, \"powerSupply\": true,
            // \"surfaceArea\": 141, \"seatCapacity\": 84, \"soundInstallation\": true, \"wheelChairAccessible\": true}
            for (int ite = 0; ite < filteredAttributes.length; ite++) {

                for (int r = 0; r < result.size(); r++) {
                    if (!result.get(r).getRoomName().equals("deleted")) {
                        object = (JSONObject) parser.parse(result.get(r).getAttributes());

                        if (ite == 0 && blinds == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 1 && desktop == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 2 && projector == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 3 && chalkBoard == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 4 && microphone == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 5 && smartBoard == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 6 && whiteBoard == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 7 && powerSupply == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 8 && soundInstallation == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }

                        if (ite == 9 && wheelChair == true) {
                            if (!Boolean.parseBoolean(object.get(filteredAttributes[ite]).toString())) {
                                result.set(r, new Room("deleted"));
                            }
                        }
                    }


                }

            }

            List<Room> resultFinal = new ArrayList<>();

            for (Room roomFinal : result) {
                if (!roomFinal.getRoomName().equals("deleted")) {
                    resultFinal.add(roomFinal);
                }
            }


            return resultFinal;
        } else {
            return new ArrayList<>();
        }


    }

    /**
     * Deletes a room based on roomId.
     *
     * @param roomId the roomId that needs to be deleted.
     */
    @DeleteMapping("room/{roomId}")
    @ResponseBody
    public void removeRoom(@PathVariable int roomId) {
        roomRepository.deleteById(roomId);

        Thread updateData = new Thread(new RoomAttributesUpdater());
        if (!RoomAttributesUpdater.isRunning()) {
            updateData.start();
        }
    }


}
