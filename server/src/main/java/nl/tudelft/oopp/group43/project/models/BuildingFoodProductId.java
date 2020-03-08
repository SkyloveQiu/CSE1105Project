package nl.tudelft.oopp.group43.project.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class BuildingFoodProductId implements java.io.Serializable {


    private int building;
    private int foodProduct;

    public BuildingFoodProductId() {
    }

    public BuildingFoodProductId(int building, int foodProduct) {
        this.building = building;
        this.foodProduct = foodProduct;
    }


    @Column(name = "building", nullable = false)
    public int getBuilding() {
        return this.building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }


    @Column(name = "food_product", nullable = false)
    public int getFoodProduct() {
        return this.foodProduct;
    }

    public void setFoodProduct(int foodProduct) {
        this.foodProduct = foodProduct;
    }

    /**
     * check if it's the same product.
     * @param other other product.
     * @return the result.
     */
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof BuildingFoodProductId)) {
            return false;
        }
        BuildingFoodProductId castOther = (BuildingFoodProductId) other;

        return (this.getBuilding() == castOther.getBuilding())
                && (this.getFoodProduct() == castOther.getFoodProduct());
    }

}


