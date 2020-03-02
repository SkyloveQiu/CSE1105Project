package nl.tudelft.oopp.group43.project.controllers;

import com.sun.xml.bind.v2.TODO;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;


@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/building")
    @ResponseBody
    public List<Building> getBuilding(){
        return repository.findAll();
    }

    //TODO Make it secure.

    @PutMapping("/building")
    @ResponseBody
    public String CreateBuilding(@RequestBody Building newBuilding){
        repository.save(newBuilding);
        return "NEW BUILDING: " + newBuilding.getBuildingName();
    }



    @GetMapping("/building2")
    @ResponseBody
    public List<Building> getBuildingJoin(){

        return repository.getJoined();

    }

    @DeleteMapping("building/{buildingNumber}")
    @ResponseBody
    public void removeFoodReservation(@PathVariable int buildingNumber) {
        repository.deleteById(buildingNumber);
    }


}
