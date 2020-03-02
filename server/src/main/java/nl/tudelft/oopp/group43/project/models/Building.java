package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import net.minidev.json.annotate.JsonIgnore;

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

    @JsonIgnore
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

    @JsonIgnore
    @OneToMany(targetEntity=FoodOrder.class, mappedBy="building", fetch=FetchType.LAZY)
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }

    @JsonSetter
    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

    @JsonIgnore
    @OneToMany(targetEntity=Room.class, mappedBy="building", fetch=FetchType.LAZY)
    public Set<Room> getRooms() {
        return this.rooms;
    }

    @JsonSetter
    public void setRooms(Set rooms) {
        this.rooms = rooms;
    }



    @JsonIgnore
    @OneToMany(targetEntity=BuildingFoodProduct.class, mappedBy="building", fetch=FetchType.LAZY)
    public Set<BuildingFoodProduct> getBuildingFoodProducts() {
        return this.buildingFoodProducts;
    }

    @JsonSetter
    public void setBuildingFoodProducts(Set buildingFoodProducts) {
        this.buildingFoodProducts = buildingFoodProducts;
    }


}


