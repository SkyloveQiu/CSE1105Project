package nl.tudelft.oopp.group43.project.models;



import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="building")
public class Building  implements java.io.Serializable {


     private int buildingNumber;
     private String buildingName;
     private String address;
     private String openingHours;
     private Set foodOrders = new HashSet(0);
     private Set rooms = new HashSet(0);
     private Set buildingFoodProducts = new HashSet(0);

    public Building() {
    }

	
    public Building(int buildingNumber, String buildingName, String address, String openingHours) {
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
   
     @Id 

    
    @Column(name="building_number", unique=true, nullable=false)
    public int getBuildingNumber() {
        return this.buildingNumber;
    }
    
    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    
    @Column(name="building_name", nullable=false)
    public String getBuildingName() {
        return this.buildingName;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    
    @Column(name="address", nullable=false)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="opening_hours", nullable=false)
    public String getOpeningHours() {
        return this.openingHours;
    }
    
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="building")
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }
    
    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="building")
    public Set<Room> getRooms() {
        return this.rooms;
    }
    
    public void setRooms(Set rooms) {
        this.rooms = rooms;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="building")
    public Set<BuildingFoodProduct> getBuildingFoodProducts() {
        return this.buildingFoodProducts;
    }
    
    public void setBuildingFoodProducts(Set buildingFoodProducts) {
        this.buildingFoodProducts = buildingFoodProducts;
    }




}


