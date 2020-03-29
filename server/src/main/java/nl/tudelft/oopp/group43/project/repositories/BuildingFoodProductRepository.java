package nl.tudelft.oopp.group43.project.repositories;

import java.util.List;
import nl.tudelft.oopp.group43.project.keys.BuildingFoodProductKey;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProductId;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingFoodProductRepository extends JpaRepository<BuildingFoodProduct, BuildingFoodProductId> {

    boolean existsBuildingFoodProductByBuildingAndAndFoodProduct(Building building, FoodProduct foodProduct);

    List<BuildingFoodProduct> findAllByBuilding(Building building);
}
