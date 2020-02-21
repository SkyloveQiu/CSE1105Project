package nl.tudelft.oopp.group43.project.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoomController {


    @Autowired
    private RoomRepository repository;

    @GetMapping("/room")
    @ResponseBody
    public List<Room> getRoom(){
        return repository.findAll();
    }


    @PutMapping("/room")
    @ResponseBody
    public String createRoom(@RequestBody Room newRoom){
        repository.save(newRoom);
        return "NEW ROOM: " + newRoom.getRoomName();
    }

}
