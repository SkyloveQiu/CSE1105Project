package nl.tudelft.oopp.group43.project.keys;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OrderDetailsKey implements Serializable {

    @Column(name = "food_order_id")
    int orderId;

    @Column(name = "food_product_id")
    int productId;

    public OrderDetailsKey() {

    }

    public OrderDetailsKey(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }


    public int getProductId() {
        return productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailsKey)) {
            return false;
        }
        OrderDetailsKey that = (OrderDetailsKey) o;
        return orderId == that.orderId
                && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
