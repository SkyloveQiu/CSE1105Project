package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;


import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


@Entity
@DynamicUpdate
@Table(name = "room")
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "building_number")
    private int buildingNumber;


    @Column(name = "attributes")
    private String attributes;


    public Room() {
    }

    public Room(@JsonProperty("id") int id,
                @JsonProperty("room_name") String roomName,
                @JsonProperty("building_number") int buildingNumber,
                @JsonProperty("attributes") String attributes){

        this.id=id;
        this.roomName=roomName;
        this.buildingNumber=buildingNumber;
        this.attributes=attributes;


    }

    public int getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public String getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return getId() == room.getId() &&
                getBuildingNumber() == room.getBuildingNumber() &&
                getRoomName().equals(room.getRoomName()) &&
                getAttributes().equals(room.getAttributes());
    }

}
