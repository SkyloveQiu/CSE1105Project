package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodOrderDetailsController {

    @Autowired
    private FoodOrderDetailsRepository repository;

    @GetMapping("/foodOrderDetails")
    @ResponseBody
    public List<FoodOrderDetails> getFoodProductDetails(){
        return repository.findAll();
    }


    @PutMapping("/foodOrderDetails")
    @ResponseBody
    public String CreateFoodProductDetails(@RequestBody FoodOrderDetails newFoodOrderDetails){
        repository.save(newFoodOrderDetails);
        return "NEW FOOD ORDER DETAILS FOR ID: " + newFoodOrderDetails.getOrderId();
    }
}
