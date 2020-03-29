package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodOrderDetailsController {

    @Autowired
    private FoodOrderDetailsRepository repository;

    /**
     * get the food orders details.
     *
     * @return all the food order details.
     */
    @GetMapping("/foodOrderDetails")
    @ResponseBody
    public List<FoodOrderDetails> getFoodOrderDetails() {
        return repository.findAll();
    }

}
