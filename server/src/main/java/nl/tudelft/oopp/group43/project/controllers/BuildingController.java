package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
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
public class BuildingController {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private UserRepository userRepository;

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
     * Creates a new building.
     *
     * @param newBuilding describes a new building
     * @return a message regarding the building
     */
    @PostMapping(value = "/building")
    @ResponseBody
    public ResponseEntity createBuilding(@RequestBody Building newBuilding,
                                         @RequestParam(value = "token", defaultValue = "invalid") String token) {
        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Building creation error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Building creation error", "Only the administrator can create new buildings.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (repository.existsBuildingByBuildingNumber(newBuilding.getBuildingNumber())) {
            ErrorResponse errorResponse = new ErrorResponse("Building creation error", "BUILDING WITH NUMBER: " + newBuilding.getBuildingNumber() + " ALREADY EXISTS.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        repository.save(newBuilding);

        ErrorResponse okResponse = new ErrorResponse("New building created", "You have successfully created a new building: " + newBuilding.getBuildingName(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);
    }

    /**
     * Updates a new building.
     *
     * @param newBuilding describes an updated building
     * @return a message regarding the building
     */
    @PostMapping(value = "/building/update")
    @ResponseBody
    public ResponseEntity updateBuilding(@RequestBody Building newBuilding, @RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Building update error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Building update error", "Only the administrator can update buildings.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        repository.save(newBuilding);
        ErrorResponse okResponse = new ErrorResponse("Update building", "UPDATED BUILDING: " + newBuilding.getBuildingName(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);

    }

    /**
     * delete the specific building.
     *
     * @param buildingId the building you want to delete.
     */
    @DeleteMapping("building/{buildingId}")
    @ResponseBody
    public ResponseEntity removeBuilding(@PathVariable int buildingId, @RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Building delete error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Building delete error", "Only the administrator can delete buildings.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        repository.deleteById(buildingId);
        ErrorResponse okResponse = new ErrorResponse("Building deleted", "DELETED BUILDING: " + buildingId, HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);
    }


}
