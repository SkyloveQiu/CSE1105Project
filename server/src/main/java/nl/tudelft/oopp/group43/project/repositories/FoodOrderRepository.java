package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Integer> {
}
