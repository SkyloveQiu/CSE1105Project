package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository repository;

    @GetMapping("/foodOrder")
    @ResponseBody
    public List<FoodOrder> getFoodOrder() {
        return repository.findAll();
    }


    @PutMapping("/foodOrder")
    @ResponseBody
    public String createFoodOrder(@RequestBody FoodOrder newFoodOrder) {
        repository.save(newFoodOrder);
        return "NEW FOOD ORDER DETAILS FOR: " + newFoodOrder.getUser() + " ORDER ID: " + newFoodOrder.getId();
    }
}
