package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class BikeController {

    @Autowired
    private BikeRepository repository;

    @GetMapping("/bike")
    @ResponseBody
    public List<Bike> getBuilding() {
        return repository.findAll();
    }

    //e.g. {"bikeId":2,"building":{"building_number":0},"bikesAvailable":5}

    /**
     * Creates a new bike entity.
     *
     * @param newBike describes a new building
     * @return a message regarding the bike
     */
    @PostMapping(value = "/bike")
    @ResponseBody
    public String createBuilding(@RequestBody Bike newBike) {

        repository.save(newBike);
        return "NEW BIKE IN BUILDING: " + newBike.getBuilding().getBuildingNumber();
    }
}
