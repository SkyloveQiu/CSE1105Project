package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodProductRepository extends JpaRepository<FoodProduct, Integer> {
}
