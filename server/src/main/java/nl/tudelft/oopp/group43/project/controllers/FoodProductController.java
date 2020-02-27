package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodProductController {

    @Autowired
    private FoodProductRepository repository;

    @GetMapping("/foodProduct")
    @ResponseBody
    public List<FoodProduct> getFoodProduct(){
        return repository.findAll();
    }


    @PutMapping("/foodProduct")
    @ResponseBody
    public String CreateFoodProduct(@RequestBody FoodProduct newFoodProduct){
        repository.save(newFoodProduct);
        return "NEW FOOD PRODUCT: " + newFoodProduct.getName();
    }
}
