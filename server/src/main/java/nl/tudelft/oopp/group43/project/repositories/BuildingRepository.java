package nl.tudelft.oopp.group43.project.repositories;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {



    Building findBuildingByBuildingNumber(int buildingNumber);

}

