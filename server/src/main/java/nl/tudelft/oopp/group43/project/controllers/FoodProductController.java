package nl.tudelft.oopp.group43.project.controllers;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodProductController {

    @Autowired
    private FoodProductRepository repository;

    /**
     * get all the food product.
     * @return the food product list.
     */
    @GetMapping("/foodProduct")
    @ResponseBody
    public List<FoodProduct> getFoodProduct() {
        return repository.findAll();
    }

    /**
     * the food product you want to put.
     * @param newFoodProduct the food.
     * @return the result.
     */
    @PutMapping("/foodProduct")
    @ResponseBody
    public String createFoodProduct(@RequestBody FoodProduct newFoodProduct) {
        repository.save(newFoodProduct);
        return "NEW FOOD PRODUCT: " + newFoodProduct.getName();
    }
}
