package nl.tudelft.oopp.group43.project.repositories;

import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    Reservation findByReservationId(int id);

    boolean existsReservationByStartingDateAndEndDateAndRoomId(Date startingDate, Date endDate, int id);

    List<Reservation> findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(
            Date startingDate,
            Date endDate,
            int roomId);

    Reservation getByReservationId(int id);

    List<Reservation> findByStartingDateGreaterThanEqualAndEndDateLessThanEqual(Date startDate, Date endDate);
}
