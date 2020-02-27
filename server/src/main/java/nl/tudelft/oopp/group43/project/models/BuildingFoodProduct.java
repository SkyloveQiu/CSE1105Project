package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import nl.tudelft.oopp.group43.project.keys.BuildingFoodProductKey;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "building_food_product")
public class BuildingFoodProduct {

    @EmbeddedId
    BuildingFoodProductKey buildingAndFoodProduct;

    @Column(name = "price")
    double price;

    public BuildingFoodProduct() {
    }

    public BuildingFoodProduct(@JsonProperty("building") int building,
                               @JsonProperty("food_product") int foodProduct,
                               @JsonProperty("price") double price) {

        buildingAndFoodProduct = new BuildingFoodProductKey(building, foodProduct);
        this.price = price;

    }

    public int getBuilding() {
        return buildingAndFoodProduct.getBuilding();
    }

    public int getFoodProduct() {
        return buildingAndFoodProduct.getFoodProduct();
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildingFoodProduct)) return false;
        BuildingFoodProduct that = (BuildingFoodProduct) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 &&
                Objects.equals(buildingAndFoodProduct, that.buildingAndFoodProduct);
    }

}
