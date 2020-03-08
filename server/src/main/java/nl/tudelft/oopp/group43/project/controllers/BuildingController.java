package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    /*
    get method to request all the building list.
     */
    @GetMapping("/building")
    @ResponseBody
    public List<Building> getBuilding() {
        return repository.findAll();
    }

    // {"building_number":33,"building_name":"EWI","address":"32","opening_hours":"32"}

    /**
     *  Creates a new building.
     * @param newBuilding describes a new building
     * @return a message regarding the building
     */
    @PostMapping(value = "/building")
    @ResponseBody
    public String createBuilding(@RequestBody Building newBuilding) {
        if (repository.existsBuildingByBuildingNumber(newBuilding.getBuildingNumber())) {
            return "BUILDING WITH NUMBER: " + newBuilding.getBuildingNumber() + " ALREADY EXISTS.";
        }

        repository.save(newBuilding);
        return "NEW BUILDING: " + newBuilding.getBuildingName();
    }

    /**
     *  Updates a new building.
     * @param newBuilding describes an updated building
     * @return a message regarding the building
     */
    @PostMapping(value = "/building/update")
    @ResponseBody
    public String updateBuilding(@RequestBody Building newBuilding) {
        repository.save(newBuilding);
        return "UPDATED BUILDING: " + newBuilding.getBuildingName();
    }

    /**
     * delete the specific building.
     *
     * @param buildingId the building you want to delete.
     */
    @DeleteMapping("building/{buildingId}")
    @ResponseBody
    public void removeBuilding(@PathVariable int buildingId) {
        repository.deleteById(buildingId);
    }


}
