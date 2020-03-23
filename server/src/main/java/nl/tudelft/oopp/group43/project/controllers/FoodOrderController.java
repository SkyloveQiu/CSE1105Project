package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderrepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FoodOrderDetailsRepository foodOrderDetailsRepository;

    @GetMapping("/foodOrder")
    @ResponseBody
    public List<FoodOrder> getFoodOrder() {
        return foodOrderrepository.findAll();
    }

    // {"building":0,"reservation":1,"user":"a@tudelft.nl","time":"17:00:00"}

    /**
     * Process a food order.
     * @param newFoodOrder describes a new FoodOrder
     * @return returns a message
     */
    @PostMapping("/foodOrder")
    @ResponseBody
    public String createFoodOrder(@RequestBody FoodOrder newFoodOrder) {


        foodOrderrepository.save(newFoodOrder);
        return "NEW FOOD ORDER DETAILS";
    }
}
