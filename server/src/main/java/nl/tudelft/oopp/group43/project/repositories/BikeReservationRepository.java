package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.BikeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeReservationRepository extends JpaRepository<BikeReservation,Integer> {
}
