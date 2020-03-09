package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingFoodProductController {
    @Autowired
    private BuildingFoodProductRepository repository;

    /**
     * get the list of building food product.
     * @return the list of building food product.
     */
    @GetMapping("/buildingFoodProduct")
    @ResponseBody
    public List<BuildingFoodProduct> getBuildingFoodProduct() {
        return repository.findAll();
    }

    /**
     * create the new building food product.
     * @param newBuildingFoodProduct the product you want to create.
     * @return the product it found.
     */
    @PutMapping("/buildingFoodProduct")
    @ResponseBody
    public String createNewBuildingFoodProduct(@RequestBody BuildingFoodProduct newBuildingFoodProduct) {
        repository.save(newBuildingFoodProduct);
        return "NEW BUILDING FOOD PRODUCT FOR BUILDING: " + newBuildingFoodProduct.getBuilding()
                + " PRICE: " + newBuildingFoodProduct.getPrice()
                + " FOOD PRODUCT: " + newBuildingFoodProduct.getFoodProduct();
    }
}
