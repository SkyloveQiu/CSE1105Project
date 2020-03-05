package nl.tudelft.oopp.group43.project.models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="room")
public class Room  implements java.io.Serializable {


     private Integer id;


     private Building building;

     private String roomName;
     private String attributes;

    public Room() {
    }

    public Room(Building building, String roomName, String attributes) {
       this.building = building;
       this.roomName = roomName;
       this.attributes = attributes;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonBackReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="building_number", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Building getBuilding() {
        return this.building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    
    @Column(name="room_name", nullable=false)
    public String getRoomName() {
        return this.roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    
    @Column(name="attributes", nullable=false, length=0)
    public String getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }




}


