package nl.tudelft.oopp.group43.project.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class FoodOrderDetailsId implements java.io.Serializable {


    private int foodOrderId;
    private int foodProductId;

    public FoodOrderDetailsId() {
    }

    public FoodOrderDetailsId(int foodOrderId, int foodProductId) {
        this.foodOrderId = foodOrderId;
        this.foodProductId = foodProductId;
    }


    @Column(name = "food_order_id", nullable = false)
    public int getFoodOrderId() {
        return this.foodOrderId;
    }

    public void setFoodOrderId(int foodOrderId) {
        this.foodOrderId = foodOrderId;
    }


    @Column(name = "food_product_id", nullable = false)
    public int getFoodProductId() {
        return this.foodProductId;
    }

    public void setFoodProductId(int foodProductId) {
        this.foodProductId = foodProductId;
    }




}


