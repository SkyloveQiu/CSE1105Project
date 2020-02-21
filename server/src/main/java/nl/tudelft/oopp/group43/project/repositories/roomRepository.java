package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Room;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface roomRepository<ID, Building> {

    //search for empty rooms in a certain building
    List<Room> searchForEmptyRooms(String building);
}
