package nl.tudelft.oopp.group43.project.controllers;

import com.sun.xml.bind.v2.TODO;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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


}