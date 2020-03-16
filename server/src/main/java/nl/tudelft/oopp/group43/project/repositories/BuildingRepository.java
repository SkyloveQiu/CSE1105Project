package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BuildingRepository extends JpaRepository<Building, Integer> {


    Building findBuildingByBuildingNumber(int buildingNumber);

    boolean existsBuildingByBuildingNumber(int buildingNumber);

}

