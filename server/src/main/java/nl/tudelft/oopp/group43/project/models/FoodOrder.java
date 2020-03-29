package nl.tudelft.oopp.group43.project.models;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date time;

    private Set<FoodOrderDetails> foodOrderDetails = new HashSet(0);

    public FoodOrder() {
    }

    /**
     * init the food order.
     *
     * @param building    the building name.
     * @param reservation the reservation of user.
     * @param user        the user.
     * @param time        the time order it .
     */
    public FoodOrder(@JsonProperty("building") int building,
                     @JsonProperty("reservation") int reservation,
                     @JsonProperty("user") String user,
                     @JsonProperty("time") Date time) {
        this.building = new Building(building);
        this.reservation = new Reservation(reservation);
        this.user = new User(user);
        this.time = time;
    }

    public FoodOrder(int reservation) {
        this.reservation = new Reservation(reservation);
    }


    /**
     * init the food order.
     *
     * @param building    the building name.
     * @param reservation the reservation of user.
     * @param user        the user.
     * @param time        the time order it .
     */
    public FoodOrder(Building building, Reservation reservation, User user, Date time) {
        this.building = building;
        this.reservation = reservation;
        this.user = user;
        this.time = time;
    }


    /**
     * init the food order.
     *
     * @param building         the building you order.
     * @param reservation      the reservation of the user.
     * @param user             the user.
     * @param time             the time you order the food.
     * @param foodOrderDetails the details of this order.
     */
    public FoodOrder(Building building,
                     Reservation reservation,
                     User user, Date time,
                     Set<FoodOrderDetails> foodOrderDetails) {
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

    //  @JsonManagedReference(value = "foodOrder")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building", nullable = false)
    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    // @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation", nullable = false)
    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    //  @JsonManagedReference
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
    @JoinColumn(name = "food_order_id")
    public Set<FoodOrderDetails> getFoodOrderDetails() {
        return this.foodOrderDetails;
    }

    public void setFoodOrderDetails(Set foodOrderDetails) {
        this.foodOrderDetails = foodOrderDetails;
    }


}


