package nl.tudelft.oopp.group43.project.models;



import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="role")
public class Role  implements java.io.Serializable {


     private Long id;
     private String name;
     private Set users = new HashSet(0);

    public Role() {
    }

    public Role(String name, Set users) {
       this.name = name;
       this.users = users;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name="name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles",  joinColumns = {
        @JoinColumn(name="roles_id", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="users_email", nullable=false, updatable=false) })
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }




}


