package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository repository;

    @GetMapping("/foodOrder")
    @ResponseBody
    public List<FoodOrder> getFoodOrder(){
        return repository.findAll();
    }


    @PutMapping("/foodOrder")
    @ResponseBody
    public String CreateFoodOrder(@RequestBody FoodOrder newFoodOrder){
        repository.save(newFoodOrder);
        return "NEW FOOD ORDER DETAILS FOR: " + newFoodOrder.getUser() + " ORDER ID: "+newFoodOrder.getId();
    }
}
