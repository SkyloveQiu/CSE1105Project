package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    @Query("SELECT b,r from Building b,Room r WHERE b = r.building")
    List<Building> getJoined();

    Building findBuildingBybuildingNumber(int buildingNumber);

}

