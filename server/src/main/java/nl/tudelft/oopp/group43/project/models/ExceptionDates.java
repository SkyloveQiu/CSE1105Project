package nl.tudelft.oopp.group43.project.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exception_dates")
public class ExceptionDates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "starting_date", length = 19)
    private Date startingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", length = 19)
    private Date endDate;

    @Column(name = "building_number", nullable = false)
    private Integer buildingNumber;

    public ExceptionDates() {
    }

    /**
     * Describes an exception date.
     *
     * @param id             the id of the exception Date
     * @param endDate        the end date of the exception
     * @param startingDate   the starting date of the exception
     * @param buildingNumber the building that has an exception
     */
    public ExceptionDates(Integer id, Date endDate, Date startingDate, Integer buildingNumber) {
        this.id = id;
        this.endDate = endDate;
        this.startingDate = startingDate;
        this.buildingNumber = buildingNumber;
    }


    public Integer getId() {
        return this.id;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Date getEndDate() {
        return this.endDate;
    }


    public Date getStartingDate() {
        return this.startingDate;
    }

}
