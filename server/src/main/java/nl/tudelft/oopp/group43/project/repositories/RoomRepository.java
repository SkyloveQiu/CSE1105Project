package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    public List<Room> findRoomByBuildingNumber(@Param("building_number") int buildingNumber);




}
