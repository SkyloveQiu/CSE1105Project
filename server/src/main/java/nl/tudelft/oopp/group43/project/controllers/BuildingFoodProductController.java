package nl.tudelft.oopp.group43.project.controllers;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingFoodProductController {
    @Autowired
    private BuildingFoodProductRepository repository;

    @Autowired
    private UserRepository userRepository;

    /**
     * get the list of building food product.
     *
     * @return the list of building food product.
     */
    @GetMapping("/buildingFoodProduct")
    @ResponseBody
    public List<BuildingFoodProduct> getBuildingFoodProduct() {
        return repository.findAll();
    }

    /**
     * create the new building food product.
     *
     * @param newBuildingFoodProduct the product you want to create.
     * @return the product it found.
     */
    @PostMapping("/buildingFoodProduct")
    @ResponseBody
    public ResponseEntity createNewBuildingFoodProduct(@RequestBody BuildingFoodProduct newBuildingFoodProduct, @RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Building food product creation error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getUsername().equals("admin@tudelft.nl")) {
            ErrorResponse errorResponse = new ErrorResponse("Building food product creation error", "Only the administrator can create new buildings.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        repository.save(newBuildingFoodProduct);
        ErrorResponse okResponse = new ErrorResponse("Building food product creation", "NEW BUILDING FOOD PRODUCT FOR BUILDING: " + newBuildingFoodProduct.getBuilding()
            + " PRICE: " + newBuildingFoodProduct.getPrice()
            + " FOOD PRODUCT: " + newBuildingFoodProduct.getFoodProduct(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);

    }

    /**
     * Returns the products in a certain building.
     *
     * @param building the building number
     * @return all the products in that building
     */
    @GetMapping("/buildingFoodProduct/moreDetails")
    @ResponseBody
    public List<BuildingFoodProduct> getBuildingFoodProductBasedOnBuilding(@RequestParam(value = "building", defaultValue = "0") int building) {
        try {
            return repository.findAllByBuilding(new Building(building));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
