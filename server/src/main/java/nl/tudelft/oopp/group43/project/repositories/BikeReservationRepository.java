package nl.tudelft.oopp.group43.project.repositories;

import java.util.List;
import nl.tudelft.oopp.group43.project.models.BikeReservation;
import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeReservationRepository extends JpaRepository<BikeReservation,Integer> {
    List<BikeReservation> findByUser(User user);

    BikeReservation findBybikeReservationId(int id);
}
