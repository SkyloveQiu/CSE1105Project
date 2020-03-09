package nl.tudelft.oopp.group43.project.keys;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BuildingFoodProductKey implements Serializable {

    @Column(name = "building")
    int building;

    @Column(name = "food_product")
    int foodProduct;

    public BuildingFoodProductKey() {
    }

    public BuildingFoodProductKey(int building, int foodProduct) {
        this.building = building;
        this.foodProduct = foodProduct;
    }

    public int getBuilding() {
        return building;
    }

    public int getFoodProduct() {
        return foodProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuildingFoodProductKey)) {
            return false;
        }
        BuildingFoodProductKey that = (BuildingFoodProductKey) o;
        return getBuilding() == that.getBuilding()
                && getFoodProduct() == that.getFoodProduct();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuilding(), getFoodProduct());
    }
}
