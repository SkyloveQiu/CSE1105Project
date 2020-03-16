package nl.tudelft.oopp.group43.project.repositories;

import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUser(User user);

    boolean existsReservationByStartingDateAndAndEndDateAndRoomId(Date startingDate, Date endDate, int id);


    List<Reservation> findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(
            Date startingDate,
            Date endDate,
            int roomId);
}
