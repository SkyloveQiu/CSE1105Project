package nl.tudelft.oopp.group43.project.models;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "food_order_details")
public class FoodOrderDetails implements java.io.Serializable {


    private FoodOrderDetailsId id;
    private FoodProduct foodProduct;
    private FoodOrder foodOrder;
    private int amount;

    public FoodOrderDetails() {
    }

    /**
     * init the food order details.
     * @param id the id.
     * @param foodProduct the product you ordered.
     * @param foodOrder the food order.
     * @param amount the amount price.
     */
    public FoodOrderDetails(FoodOrderDetailsId id, FoodProduct foodProduct, FoodOrder foodOrder, int amount) {
        this.id = id;
        this.foodProduct = foodProduct;
        this.foodOrder = foodOrder;
        this.amount = amount;
    }

    @EmbeddedId


    @AttributeOverrides({
            @AttributeOverride(name = "foodOrderId", column = @Column(name = "food_order_id", nullable = false)),
            @AttributeOverride(name = "foodProductId", column = @Column(name = "food_product_id", nullable = false))})
    public FoodOrderDetailsId getId() {
        return this.id;
    }

    public void setId(FoodOrderDetailsId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_product_id", nullable = false, insertable = false, updatable = false)
    public FoodProduct getFoodProduct() {
        return this.foodProduct;
    }

    public void setFoodProduct(FoodProduct foodProduct) {
        this.foodProduct = foodProduct;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_order_id", nullable = false, insertable = false, updatable = false)
    public FoodOrder getFoodOrder() {
        return this.foodOrder;
    }

    public void setFoodOrder(FoodOrder foodOrder) {
        this.foodOrder = foodOrder;
    }


    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}


