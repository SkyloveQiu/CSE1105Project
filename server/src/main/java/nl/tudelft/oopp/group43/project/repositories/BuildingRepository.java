package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
}

