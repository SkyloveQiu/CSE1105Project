package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.keys.OrderDetailsKey;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface FoodOrderDetailsRepository extends JpaRepository<FoodOrderDetails, OrderDetailsKey> {
}
