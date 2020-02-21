package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    int reservationId;

    @Column(name = "user_id")
    String userId;

    @Column(name = "room_id")
    int roomId;

    @Column(name = "starting_date")
    java.util.Date startingDate;

    @Column(name = "end_date")
    java.util.Date endDate;

    public Reservation(){}

    public Reservation(@JsonProperty("reservation_id") int reservationId,
                       @JsonProperty("user_id") String userId,
                       @JsonProperty("room_id") int roomId,
                       @JsonProperty("starting_date") java.util.Date startingDate,
                       @JsonProperty("end_date") java.util.Date endDate){
        this.reservationId=reservationId;
        this.userId=userId;
        this.roomId=roomId;
        this.startingDate=startingDate;
        this.endDate=endDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return getReservationId() == that.getReservationId() &&
                getRoomId() == that.getRoomId() &&
                getUserId().equals(that.getUserId()) &&
                getStartingDate().equals(that.getStartingDate()) &&
                getEndDate().equals(that.getEndDate());
    }

}
