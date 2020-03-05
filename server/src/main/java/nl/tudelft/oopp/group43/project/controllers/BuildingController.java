package nl.tudelft.oopp.group43.project.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sun.xml.bind.v2.TODO;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.hibernate.HibernateException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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

// {"building_number":33,"building_name":"EWI","address":"32","opening_hours":"32"}
    @PostMapping(value = "/building")
    @ResponseBody
    public String CreateBuilding(@RequestBody Building newBuilding){
        repository.save(newBuilding);
        return "NEW BUILDING: " + newBuilding.getBuildingName();
    }


    @DeleteMapping("building/{buildingId}")
    @ResponseBody
    public void removeBuilding(@PathVariable int buildingId) {
        repository.deleteById(buildingId);
    }


}
