package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodProductController {

    @Autowired
    private FoodProductRepository repository;

    @Autowired
    private UserRepository userRepository;

    /**
     * get all the food product.
     *
     * @return the food product list.
     */
    @GetMapping("/foodProduct")
    @ResponseBody
    public List<FoodProduct> getFoodProduct() {
        return repository.findAll();
    }

    /**
     * the food product you want to put.
     *
     * @param newFoodProduct the food.
     * @return the result.
     */
    @PostMapping("/foodProduct")
    @ResponseBody
    public ResponseEntity createFoodProduct(@RequestBody FoodProduct newFoodProduct, @RequestParam(value = "token", defaultValue = "invalid") String token) {

        if (token.equals("invalid")) {
            ErrorResponse errorResponse = new ErrorResponse("Building food product add/update error", "Check if you sent the token", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        if (userRepository.findUserByToken(token) == null || !userRepository.findUserByToken(token).getRole().equals("admin")) {
            ErrorResponse errorResponse = new ErrorResponse("Building food product add/update error", "Only the administrator can add/update food products.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        repository.save(newFoodProduct);
        ErrorResponse okResponse = new ErrorResponse("Update FoodProduct", "UPDATED FoodProduct: " + newFoodProduct.getName(), HttpStatus.OK.value());
        return new ResponseEntity<>(okResponse, HttpStatus.OK);
    }
}
