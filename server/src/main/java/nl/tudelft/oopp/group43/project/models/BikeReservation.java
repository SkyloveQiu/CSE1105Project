package nl.tudelft.oopp.group43.project.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "bike_reservation")
public class BikeReservation implements java.io.Serializable {

    private Integer bikeReservationId;
    private Building buildingByBuildingEnd;
    private Building buildingByBuildingStart;
    private Date datetimeStart;
    private Date datetimeEnd;
    private User user;
    private Bike bike;

    public BikeReservation() {
    }

    /**
     * init a new bikeReservation.
     *
     * @param buildingByBuildingStart the building by opening hours
     * @param datetimeStart           the time of the opening hours
     */
    public BikeReservation(Building buildingByBuildingStart, Date datetimeStart, User user, Bike bike) {
        this.buildingByBuildingStart = buildingByBuildingStart;
        this.datetimeStart = datetimeStart;
        this.user = user;
        this.bike = bike;
    }

    /**
     * init a new bikeReservation.
     *
     * @param buildingByBuildingEnd   the building by closing hours
     * @param buildingByBuildingStart the building by opening hours
     * @param datetimeStart           the date and time when program starts
     * @param datetimeEnd             the date and time when program ends
     */
    public BikeReservation(Building buildingByBuildingEnd, Building buildingByBuildingStart, Date datetimeStart,
                           Date datetimeEnd) {
        this.buildingByBuildingEnd = buildingByBuildingEnd;
        this.buildingByBuildingStart = buildingByBuildingStart;
        this.datetimeStart = datetimeStart;
        this.datetimeEnd = datetimeEnd;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "bike_reservation_id", unique = true, nullable = false)
    public Integer getBikeReservationId() {
        return this.bikeReservationId;
    }

    public void setBikeReservationId(Integer bikeReservationId) {
        this.bikeReservationId = bikeReservationId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_end")
    public Building getBuildingByBuildingEnd() {
        return this.buildingByBuildingEnd;
    }

    public void setBuildingByBuildingEnd(Building buildingByBuildingEnd) {
        this.buildingByBuildingEnd = buildingByBuildingEnd;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_start", nullable = false)
    public Building getBuildingByBuildingStart() {
        return this.buildingByBuildingStart;
    }

    public void setBuildingByBuildingStart(Building buildingByBuildingStart) {
        this.buildingByBuildingStart = buildingByBuildingStart;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike", nullable = false)
    public Bike getBike() {
        return this.bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_start", nullable = false, length = 10)
    public Date getDatetimeStart() {
        return this.datetimeStart;
    }

    public void setDatetimeStart(Date datetimeStart) {
        this.datetimeStart = datetimeStart;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_end", length = 10)
    public Date getDatetimeEnd() {
        return this.datetimeEnd;
    }

    public void setDatetimeEnd(Date datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

}
