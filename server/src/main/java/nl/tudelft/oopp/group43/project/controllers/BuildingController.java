package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @GetMapping("/building")
    @ResponseBody
    public List<Building> getBuilding(){
        return repository.findAll();
    }


    @PutMapping("/building")
    @ResponseBody
    public String CreateBuilding(@RequestBody Building newBuilding){
        repository.save(newBuilding);
        return "NEW BUILDING: " + newBuilding.getBuildingName();
    }
    

}
