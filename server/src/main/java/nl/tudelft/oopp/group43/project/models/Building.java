package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
    @OneToMany(targetEntity = FoodOrder.class, mappedBy = "building", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FoodOrder> foodOrders = new HashSet(0);

    @ElementCollection
    @OneToMany(targetEntity = Room.class, mappedBy = "building", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Room> rooms = new HashSet(0);

    @ElementCollection
    @OneToMany(targetEntity = BuildingFoodProduct.class, mappedBy = "building", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BuildingFoodProduct> buildingFoodProducts = new HashSet(0);

    @ElementCollection
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buildingByBuildingEnd")
    private Set<BikeReservation> bikeReservationsForBuildingEnd = new HashSet(0);

    @ElementCollection
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buildingByBuildingStart")
    private Set<BikeReservation> bikeReservationsForBuildingStart = new HashSet(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "building")
    private Set<Bike> bikes = new HashSet(0);

    public Building() {
    }

    /**
     * create the init of building .
     *
     * @param buildingNumber the number of building.
     * @param buildingName   the name of building.
     * @param address        the address of building.
     * @param openingHours   the opening hours of building.
     */
    @JsonCreator
    public Building(@JsonProperty("building_number") int buildingNumber,
                    @JsonProperty("building_name") String buildingName,
                    @JsonProperty("address") String address,
                    @JsonProperty("opening_hours") String openingHours) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.address = address;
        this.openingHours = openingHours;
    }

    public Building(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    /**
     * create the init of building with additions.
     *
     * @param buildingNumber                   the number of building.
     * @param buildingName                     the name of building.
     * @param address                          the address of building.
     * @param openingHours                     the opening hours of building.
     * @param bikeReservationsForBuildingEnd   the end reservation for building
     * @param bikeReservationsForBuildingStart the start reservation for end
     * @param foodOrders                       the food orders assigned to a building
     * @param rooms                            the rooms in a certain building
     * @param bikes                            the bikes in a certain building
     * @param buildingFoodProducts             the food products available in a building
     */
    public Building(int buildingNumber, String buildingName, String address, String openingHours,
                    Set bikeReservationsForBuildingEnd, Set bikeReservationsForBuildingStart, Set foodOrders, Set rooms,
                    Set bikes, Set buildingFoodProducts) {
        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.address = address;
        this.openingHours = openingHours;
        this.bikeReservationsForBuildingEnd = bikeReservationsForBuildingEnd;
        this.bikeReservationsForBuildingStart = bikeReservationsForBuildingStart;
        this.foodOrders = foodOrders;
        this.rooms = rooms;
        this.bikes = bikes;
        this.buildingFoodProducts = buildingFoodProducts;
    }

    public Building(String buildingName) {
        this.buildingName = buildingName;

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

    @JsonBackReference(value = "foodOrder")
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }


    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

    @JsonBackReference(value = "room")
    public Set<Room> getRooms() {
        return this.rooms;
    }


    public void setRooms(Set rooms) {
        this.rooms = rooms;
    }


    @JsonBackReference(value = "buildingFoodProducts")
    public Set<BuildingFoodProduct> getBuildingFoodProducts() {
        return this.buildingFoodProducts;
    }


    public void setBuildingFoodProducts(Set buildingFoodProducts) {
        this.buildingFoodProducts = buildingFoodProducts;
    }


    @JsonBackReference(value = "buildingByBuildingEnd")
    public Set getBikeReservationsForBuildingEnd() {
        return this.bikeReservationsForBuildingEnd;
    }

    public void setBikeReservationsForBuildingEnd(Set bikeReservationsForBuildingEnd) {
        this.bikeReservationsForBuildingEnd = bikeReservationsForBuildingEnd;
    }


    @JsonBackReference(value = "buildingByBuildingStart")
    public Set getBikeReservationsForBuildingStart() {
        return this.bikeReservationsForBuildingStart;
    }

    public void setBikeReservationsForBuildingStart(Set bikeReservationsForBuildingStart) {
        this.bikeReservationsForBuildingStart = bikeReservationsForBuildingStart;
    }


    @JsonBackReference(value = "bike")
    public Set getBikes() {
        return this.bikes;
    }

    public void setBikes(Set bikes) {
        this.bikes = bikes;
    }


}


