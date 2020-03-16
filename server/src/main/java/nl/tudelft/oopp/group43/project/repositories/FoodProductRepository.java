package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.FoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface FoodProductRepository extends JpaRepository<FoodProduct, Integer> {
}
