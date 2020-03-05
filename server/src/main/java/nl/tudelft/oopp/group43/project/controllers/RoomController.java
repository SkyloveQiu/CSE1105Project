package nl.tudelft.oopp.group43.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/room")
    @ResponseBody
    public String createRoom(@RequestBody Room newRoom) {
        roomRepository.save(newRoom);
        return "NEW ROOM: " + newRoom.getRoomName();
    }

    /**
     * get the building.
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

    @DeleteMapping("room/{roomId}")
    @ResponseBody
    public void removeRoom(@PathVariable int roomId) {
        roomRepository.deleteById(roomId);
    }

}
