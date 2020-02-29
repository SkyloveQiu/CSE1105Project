package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import nl.tudelft.oopp.group43.utilities.GenerateRandomSalt;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "building_number")
    int buildingNumber;


    @Column(name = "building_name")
    String buildingName;

    @Column(name = "address")
    String address;

    @OneToMany
    private List<FoodOrder> foodOrders;

    @OneToMany
    private List<BuildingFoodProduct> buildingFoodProducts;

    @OneToMany(mappedBy = "building")
     private Set<Room> rooms;




    public Building() {
    }

    public Building(@JsonProperty("building_number") int buildingNumber,
                    @JsonProperty("building_name") String buildingName,
                    @JsonProperty("address") String address) {

        this.buildingNumber = buildingNumber;
        this.buildingName = buildingName;
        this.address = address;


    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Building)) return false;
        Building building = (Building) o;
        return getBuildingNumber() == building.getBuildingNumber() &&
                getBuildingName().equals(building.getBuildingName()) &&
                getAddress().equals(building.getAddress());
    }

}
