package nl.tudelft.oopp.group43.project.repositories;

import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.ExceptionDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionDatesRepository extends JpaRepository<ExceptionDates, String> {
    List<ExceptionDates> findAllByBuildingNumber(int id);

    @Query("SELECT case when count(e) > 0 then true else false end from ExceptionDates e where e.endDate > ?1 and e.startingDate < ?1 and e.buildingNumber = ?2")
    boolean existsExceptionDateByQuery(Date date, int buildingNumber);
}
