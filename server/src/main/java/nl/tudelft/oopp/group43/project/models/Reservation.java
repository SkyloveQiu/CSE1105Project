package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "reservation")
public class Reservation implements java.io.Serializable {


    private Integer reservationId;
    private User user;
    private Integer roomId;
    private Date startingDate;
    private Date endDate;

    @JsonBackReference(value = "foodOrder")
    private Set<FoodOrder> foodOrders = new HashSet(0);

    public Reservation() {
    }

    /**
     * init the reservation.
     * @param user the user you need to init.
     * @param roomId the roomid he want to book.
     * @param startingDate the starting date he wants to start.
     * @param endDate the end date he wants to end.
     */
    public Reservation(@JsonProperty("user") User user,
                       @JsonProperty("room_id") Integer roomId,
                       @JsonProperty("starting_date") Date startingDate,
                       @JsonProperty("end_date") Date endDate) {
        this.user = user;
        this.roomId = roomId;
        this.startingDate = startingDate;
        this.endDate = endDate;

    }

    /**
     * init reservation with food.
     * @param user the user you want to add.
     * @param roomId the room people want to reserve.
     * @param startingDate the start date.
     * @param endDate the end date.
     * @param foodOrders the food user booked.
     */
    public Reservation(User user, Integer roomId, Date startingDate, Date endDate, Set foodOrders) {
        this.user = user;
        this.roomId = roomId;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.foodOrders = foodOrders;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id", unique = true, nullable = false)
    public Integer getReservationId() {
        return this.reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column(name = "room_id")
    public Integer getRoomId() {
        return this.roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "starting_date", length = 19)
    public Date getStartingDate() {
        return this.startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", length = 19)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }

    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }


}


