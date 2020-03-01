package nl.tudelft.oopp.group43.project.models;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="room"
    , uniqueConstraints = @UniqueConstraint(columnNames={"room_name", "building_number"})
)
public class Room  implements java.io.Serializable {


     private Integer id;
     private Building building;
     private String roomName;
     private char attributes;

    public Room() {
    }

    public Room(Building building, String roomName, char attributes) {
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

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="building_number", nullable=false)
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
    public char getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(char attributes) {
        this.attributes = attributes;
    }




}


