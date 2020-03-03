package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "building")
public class Building implements java.io.Serializable {

    @Id
    @Column(name = "building_number", unique = true, nullable = false)
    private int buildingNumber;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "opening_hours", nullable = false)
    private String openingHours;

    @ElementCollection
    private Set<FoodOrder> foodOrders = new HashSet(0);

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet(0);

    @ElementCollection
    private Set<BuildingFoodProduct> buildingFoodProducts = new HashSet(0);

    public Building() {
    }

    public Building(@JsonProperty("building_number") int buildingNumber,
                    @JsonProperty("building_name")String buildingName,
                    @JsonProperty("address")String address,
                    @JsonProperty("opening_hours")String openingHours) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.address = address;
        this.openingHours = openingHours;
    }


    public Building(int buildingNumber, String buildingName, String address, String openingHours, Set foodOrders, Set rooms, Set buildingFoodProducts) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.address = address;
        this.openingHours = openingHours;
        this.foodOrders = foodOrders;
        this.rooms = rooms;
        this.buildingFoodProducts = buildingFoodProducts;
    }


    public int getBuildingNumber() {
        return this.buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }



    public String getBuildingName() {
        return this.buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }



    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getOpeningHours() {
        return this.openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }


    @OneToMany(targetEntity=FoodOrder.class, mappedBy="building", fetch=FetchType.LAZY, cascade = CascadeType.ALL , orphanRemoval = true )
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }


    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

    @JsonManagedReference
    @OneToMany(targetEntity=Room.class, mappedBy="building", fetch=FetchType.EAGER,cascade = CascadeType.REMOVE,orphanRemoval = true)
    public Set<Room> getRooms() {
        return this.rooms;
    }


    public void setRooms(Set rooms) {
        this.rooms = rooms;
    }




    @OneToMany(targetEntity=BuildingFoodProduct.class, mappedBy="building", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<BuildingFoodProduct> getBuildingFoodProducts() {
        return this.buildingFoodProducts;
    }


    public void setBuildingFoodProducts(Set buildingFoodProducts) {
        this.buildingFoodProducts = buildingFoodProducts;
    }


}


