package nl.tudelft.oopp.group43.project.models;



import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="user"
    ,catalog="CSE1105Project"
)
public class User  implements java.io.Serializable {


     private String email;
     private String firstName;
     private String lastName;
     private String password;
     private String role;
     private String token;
     private Set reservations = new HashSet(0);
     private Set foodOrders = new HashSet(0);
     private Set roles = new HashSet(0);

    public User() {
    }

	
    public User(String email) {
        this.email = email;
    }
    public User(String email, String firstName, String lastName, String password, String role, String token, Set reservations, Set foodOrders, Set roles) {
       this.email = email;
       this.firstName = firstName;
       this.lastName = lastName;
       this.password = password;
       this.role = role;
       this.token = token;
       this.reservations = reservations;
       this.foodOrders = foodOrders;
       this.roles = roles;
    }
   
     @Id 

    
    @Column(name="email", unique=true, nullable=false)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="first_name")
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name="last_name")
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    @Column(name="password")
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return this.email;
    }
    
    @Column(name="role")
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    
    @Column(name="token")
    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getReservations() {
        return this.reservations;
    }
    
    public void setReservations(Set reservations) {
        this.reservations = reservations;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getFoodOrders() {
        return this.foodOrders;
    }
    
    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles", catalog="CSE1105Project", joinColumns = { 
        @JoinColumn(name="users_email", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="roles_id", nullable=false, updatable=false) })
    public Set getRoles() {
        return this.roles;
    }
    
    public void setRoles(Set roles) {
        this.roles = roles;
    }




}


