package nl.tudelft.oopp.group43.project.repositories;

import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.ExceptionDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionDatesRepository extends JpaRepository<ExceptionDates,String> {
    List<ExceptionDates> findAllByBuildingNumber(int id);

    boolean existsExceptionDatesByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndBuildingNumber(Date startingDate,Date endDate, int id);
}
