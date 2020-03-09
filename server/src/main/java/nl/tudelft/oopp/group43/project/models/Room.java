package nl.tudelft.oopp.group43.project.models;

import static javax.persistence.GenerationType.IDENTITY;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "room")
public class Room implements java.io.Serializable {


    private Integer id;

    private Building building;

    private String roomName;

    private String attributes;

    public Room() {
    }

    /**
     * init the building.
     * @param building the building number.
     * @param roomName the room number.
     * @param attributes the content of the room.
     */
    public Room(@JsonProperty("building") Building building,
                @JsonProperty("room_name") String roomName,
                @JsonProperty("attributes") String attributes) {
        this.building = building;
        this.roomName = roomName;
        this.attributes = attributes;
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


    @JsonManagedReference(value = "room")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_number", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Building getBuilding() {
        return this.building;
    }


    public void setBuilding(Building building) {
        this.building = building;
    }


    @Column(name = "room_name", nullable = false)
    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    @Column(name = "attributes", nullable = false)
    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }


}


