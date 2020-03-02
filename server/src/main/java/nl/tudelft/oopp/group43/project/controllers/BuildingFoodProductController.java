package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BuildingFoodProductController {
    @Autowired
    private BuildingFoodProductRepository repository;

    @GetMapping("/buildingFoodProduct")
    @ResponseBody
    public List<BuildingFoodProduct> getBuildingFoodProduct(){
        return repository.findAll();
    }


    @PutMapping("/buildingFoodProduct")
    @ResponseBody
    public String CreatenewBuildingFoodProduct(@RequestBody BuildingFoodProduct newBuildingFoodProduct){
        repository.save(newBuildingFoodProduct);
        return "NEW BUILDING FOOD PRODUCT FOR BUILDING: " + newBuildingFoodProduct.getBuilding()
                +" PRICE: " +newBuildingFoodProduct.getPrice() +
                " FOOD PRODUCT: " + newBuildingFoodProduct.getFoodProduct();
    }
}
