package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.tudelft.oopp.group43.project.keys.OrderDetailsKey;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "food_order_details")
public class FoodOrderDetails implements Serializable {

    @EmbeddedId
    private OrderDetailsKey orderIdPlusProductId;

    @Column(name = "amount")
    int amount;

    public FoodOrderDetails() {
    }

    public FoodOrderDetails(@JsonProperty("food_order_id") int orderId,
                            @JsonProperty("food_product_id") int productId,
                            @JsonProperty("amount") int amount) {


        orderIdPlusProductId = new OrderDetailsKey(orderId,productId);
        this.amount=amount;
    }

    public int getOrderId() {
        return orderIdPlusProductId.getOrderId();
    }

    public int getProductId() {
        return orderIdPlusProductId.getProductId();
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodOrderDetails)) return false;
        FoodOrderDetails that = (FoodOrderDetails) o;
        return getOrderId() == that.getOrderId() &&
                getProductId() == that.getProductId() &&
                getAmount() == that.getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getProductId());
    }
}
