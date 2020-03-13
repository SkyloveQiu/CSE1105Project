package nl.tudelft.oopp.group43.project.models;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "bike")
public class Bike implements java.io.Serializable {

    private Integer bikeId;
    private Building building;
    private int bikesAvailable;

    public Bike() {
    }

    public Bike(@JsonProperty("building") Building building,
                @JsonProperty("bikes_available") int bikesAvailable) {
        this.building = building;
        this.bikesAvailable = bikesAvailable;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "bike_id", unique = true, nullable = false)
    public Integer getBikeId() {
        return this.bikeId;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }


    //optional JsonIgnore. If you want to see also info related to the building delete the following JsonIgnore
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false)
    @JsonManagedReference(value = "bike")
    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @Column(name = "bikes_available", nullable = false)
    public int getBikesAvailable() {
        return this.bikesAvailable;
    }

    public void setBikesAvailable(int bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }

}
