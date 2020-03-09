package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
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
@Table(name = "building_food_product")
public class BuildingFoodProduct implements java.io.Serializable {


    private BuildingFoodProductId id;
    private Building building;
    private FoodProduct foodProduct;
    private BigDecimal price;

    public BuildingFoodProduct() {
    }

    /**
     * init the building food product.
     *
     * @param id          the id of food.
     * @param building    the building number.
     * @param foodProduct the product.
     */
    public BuildingFoodProduct(BuildingFoodProductId id, Building building, FoodProduct foodProduct) {
        this.id = id;
        this.building = building;
        this.foodProduct = foodProduct;
    }

    /**
     * init the building food product.
     *
     * @param id          the id of food.
     * @param building    the building number.
     * @param foodProduct the product.
     * @param price       the price of the product.
     */
    public BuildingFoodProduct(BuildingFoodProductId id, Building building, FoodProduct foodProduct, BigDecimal price) {
        this.id = id;
        this.building = building;
        this.foodProduct = foodProduct;
        this.price = price;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "building", column = @Column(name = "building", nullable = false)),
            @AttributeOverride(name = "foodProduct", column = @Column(name = "food_product", nullable = false))})
    public BuildingFoodProductId getId() {
        return this.id;
    }

    public void setId(BuildingFoodProductId id) {
        this.id = id;
    }

    @JsonManagedReference(value = "buildingFoodProducts")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building", nullable = false, insertable = false, updatable = false)
    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_product", nullable = false, insertable = false, updatable = false)
    public FoodProduct getFoodProduct() {
        return this.foodProduct;
    }

    public void setFoodProduct(FoodProduct foodProduct) {
        this.foodProduct = foodProduct;
    }


    @Column(name = "price", precision = 10)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}


