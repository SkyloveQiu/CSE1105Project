package nl.tudelft.oopp.group43.project.repositories;

import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import nl.tudelft.oopp.group43.project.models.User;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    boolean existsReservationByStartingDateAndAndEndDateAndRoomId(Date startingDate, Date endDate, int id);

    
    List<Reservation> findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(
            Date startingDate,
            Date endDate,
            int roomId);
}
