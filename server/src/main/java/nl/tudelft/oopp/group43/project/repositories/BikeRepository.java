package nl.tudelft.oopp.group43.project.repositories;

import java.util.List;

import nl.tudelft.oopp.group43.project.keys.BuildingFoodProductKey;
import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {

}
