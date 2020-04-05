package nl.tudelft.oopp.group43.project.controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;

import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity createRoom(@RequestBody Room newRoom, @RequestParam(value = "token", defaultValue = "invalid") String token) {


        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Room creation error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Room creation error", "Only the administrator can create new rooms.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }


        roomRepository.save(newRoom);
        Thread updateData = new Thread(new RoomAttributesUpdater());
        if (!RoomAttributesUpdater.isRunning()) {
            updateData.start();
        }

        ErrorResponse okResponse = new ErrorResponse("New building created", "NEW ROOM: " + newRoom.getRoomName(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);


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

        Iterator<Room> roomIterator = roomSet.iterator();
        List<Room> roomList = new ArrayList<Room>();
        while (roomIterator.hasNext()) {
            roomList.add(roomIterator.next());
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
     * @param type              if the selected type matches the filtered type
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
                                       @RequestParam(value = "type", defaultValue = "ignored") String type,
                                       @RequestParam(value = "employee", defaultValue = "false") boolean employee,
                                       @RequestParam(value = "minSpace", defaultValue = "0") int minSpace) throws ParseException, CloneNotSupportedException {

        final String[] filteredAttributes = {"blinds",
            "desktopPc",
            "projector",
            "chalkBoard",
            "microphone",
            "smartBoard",
            "whiteBoard",
            "powerSupply",
            "soundInstallation",
            "wheelChairAccessible",
            "spaceType",
            "Only_Employee"};

        int status = 0;

        if (!RoomAttributesUpdater.isRunning()) {

            List<Room> result = new ArrayList<>();

            List<Integer> validRooms = RoomAttributesUpdater.list_higher_than(minSpace);

            List<Room>[] roomListData = RoomAttributesUpdater.getRoomList().getBuckets();

            Hashtable tableRooms = (Hashtable) RoomAttributesUpdater.getTableRooms();

            JSONParser parser = new JSONParser();
            JSONObject object;


            for (Integer i : validRooms) {
                for (Room room : roomListData[i]) {
                    Room a = (Room) ((Room) tableRooms.get(room.getId())).clone();
                    a.setId(room.getId());
                    result.add(a);

                }
            }
            //{\"blinds\": true, \"display\": false, \"desktopPc\": true,
            // \"projector\": true, \"spaceType\": \"Instruction hall\", \"chalkBoard\": true,
            // \"microphone\": false, \"smartBoard\": false, \"whiteBoard\": false, \"powerSupply\": true,
            // \"surfaceArea\": 141, \"seatCapacity\": 84, \"soundInstallation\": true, \"wheelChairAccessible\": true}
            for (int r = 0; r < result.size(); r++) {
                status = 0;
                if (RoomAttributesUpdater.verify(result.get(r))) {
                    object = (JSONObject) parser.parse(result.get(r).getAttributes());

                    if (blinds == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[0]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (desktop == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[1]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (projector == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[2]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (chalkBoard == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[3]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (microphone == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[4]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (smartBoard == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[5]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (whiteBoard == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[6]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (powerSupply == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[7]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (soundInstallation == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[8]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (wheelChair == true) {
                        if (!Boolean.parseBoolean(object.get(filteredAttributes[9]).toString())) {
                            result.get(r).setRoomName("deleted");
                        }
                    }

                    if (!type.equals("ignored")) {
                        if (object.get(filteredAttributes[10]).equals(type)) {
                            {
                                status = 1;
                            }
                        } else if (object.get(filteredAttributes[10]).equals(type)) {
                            {
                                status = 1;
                            }
                        } else if (object.get(filteredAttributes[10]).equals(type)) {
                            {
                                status = 1;
                            }
                        } else if (object.get(filteredAttributes[10]).equals(type)) {
                            {
                                status = 1;
                            }
                        } else if (object.get(filteredAttributes[10]).equals(type)) {
                            {
                                status = 1;
                            }
                        }

                        if (status == 0) {
                            result.get(r).setRoomName("deleted");
                        }

                    }


                    if (employee == true) {
                        if (object.get(filteredAttributes[11]) == null) {
                            result.get(r).setRoomName("deleted");
                        }

                        if (object.get(filteredAttributes[11]) != null && !Boolean.parseBoolean(object.get(filteredAttributes[11]).toString())) {
                            result.get(r).setRoomName("deleted");
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
    public ResponseEntity removeRoom(@PathVariable int roomId, @RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Room delete error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Room delete error", "Only the administrator can delete rooms.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        roomRepository.deleteById(roomId);

        Thread updateData = new Thread(new RoomAttributesUpdater());
        if (!RoomAttributesUpdater.isRunning()) {
            updateData.start();
        }

        ErrorResponse okResponse = new ErrorResponse("Room deleted", "DELETED ROOM: " + roomId, HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);
    }

    /**
     * get the roomName.
     *
     * @param roomId the number of building you want to get.
     * @return the result.
     */
    @GetMapping("/room/getName/{roomId}")
    @ResponseBody
    public String getRoomNameBasedOnRoomId(@PathVariable int roomId) {
        Room room = roomRepository.getRoomById(roomId);
        return room.getRoomName();
    }


}
