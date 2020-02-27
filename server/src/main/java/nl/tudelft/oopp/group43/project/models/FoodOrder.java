package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "food_order")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "user")
    String user;

    @Column(name = "building")
    int building;

    @Column(name = "reservation")
    int reservation;

    @Column(name = "time")
    java.util.Date time;


    public FoodOrder() {
    }

    public FoodOrder(@JsonProperty("id") int id,
                     @JsonProperty("user") String user,
                     @JsonProperty("building") int building,
                     @JsonProperty("reservation") int reservation,
                     @JsonProperty("time") java.util.Date time) {

        this.id=id;
        this.user=user;
        this.building=building;
        this.reservation=reservation;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getBuilding() {
        return building;
    }

    public int getReservation() {
        return reservation;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodOrder)) return false;
        FoodOrder foodOrder = (FoodOrder) o;
        return getId() == foodOrder.getId() &&
                getBuilding() == foodOrder.getBuilding() &&
                getReservation() == foodOrder.getReservation() &&
                Objects.equals(getUser(), foodOrder.getUser()) &&
                Objects.equals(getTime(), foodOrder.getTime());
    }

}
