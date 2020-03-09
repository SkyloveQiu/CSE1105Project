package nl.tudelft.oopp.group43.project.models;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




@Entity
@Table(name = "food_order")
public class FoodOrder implements java.io.Serializable {


    private Integer id;
    private Building building;
    private Reservation reservation;
    private User user;
    private Date time;
    private Set foodOrderDetails = new HashSet(0);

    public FoodOrder() {
    }

    /**
     * init the food order.
     * @param building the building name.
     * @param reservation the reservation of user.
     * @param user the user.
     * @param time the time order it .
     */
    public FoodOrder(Building building, Reservation reservation, User user, Date time) {
        this.building = building;
        this.reservation = reservation;
        this.user = user;
        this.time = time;
    }

    /**
     * init the food order.
     * @param building the building you order.
     * @param reservation the reservation of the user.
     * @param user the user.
     * @param time the time you order the food.
     * @param foodOrderDetails the details of this order.
     */
    public FoodOrder(Building building, Reservation reservation, User user, Date time, Set foodOrderDetails) {
        this.building = building;
        this.reservation = reservation;
        this.user = user;
        this.time = time;
        this.foodOrderDetails = foodOrderDetails;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)


    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonManagedReference(value = "foodOrder")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building", nullable = false)
    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation", nullable = false)
    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "time", nullable = false, length = 8)
    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn
    public Set<FoodOrder> getFoodOrderDetails() {
        return this.foodOrderDetails;
    }

    public void setFoodOrderDetails(Set foodOrderDetails) {
        this.foodOrderDetails = foodOrderDetails;
    }


}


