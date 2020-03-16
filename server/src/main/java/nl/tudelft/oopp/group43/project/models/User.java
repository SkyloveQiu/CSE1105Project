package nl.tudelft.oopp.group43.project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "user")
public class User implements java.io.Serializable {


    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
    private String token;


    private Set<Reservation> reservations = new HashSet(0);

    private Set<FoodOrder> foodOrders = new HashSet(0);

    private Set<Role> roles = new HashSet(0);

    private Set<BikeReservation> bikeReservations = new HashSet(0);

    public User() {
    }


    public User(String email) {
        this.username = email;
    }

    /**
     * init the user.
     *
     * @param email        the email.
     * @param firstName    the first name.
     * @param lastName     the last name.
     * @param password     the password.
     * @param role         the role.
     * @param token        the token of the user.
     * @param reservations the reservation connect to the user.
     * @param foodOrders   the foods he orders.
     * @param roles        the roles.
     */
    public User(String email, String firstName, String lastName, String password, String role, String token, Set reservations, Set foodOrders, Set roles, Set bikeReservations) {
        this.username = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.token = token;
        this.reservations = reservations;
        this.foodOrders = foodOrders;
        this.roles = roles;
        this.bikeReservations = bikeReservations;
    }

    /**
     * creat the user when he register.
     *
     * @param password  the password.
     * @param firstName the first name.
     * @param lastName  the last name.
     * @param username  the email he submit.
     * @param role      the role.
     */
    @JsonCreator
    public User(@JsonProperty("password") String password,
                @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName,
                @JsonProperty("username") String username,
                @JsonProperty("role") String role
    ) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }


    @Id
    @Column(name = "email", unique = true, nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String email) {
        this.username = email;
    }


    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonIgnore
    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @JsonIgnore
    @Column(name = "role")
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    @Column(name = "token")
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set reservations) {
        this.reservations = reservations;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<FoodOrder> getFoodOrders() {
        return this.foodOrders;
    }

    public void setFoodOrders(Set foodOrders) {
        this.foodOrders = foodOrders;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "users_email", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "roles_id", nullable = false, updatable = false)})
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<BikeReservation> getBikeReservations() {
        return this.bikeReservations;
    }

    public void setBikeReservations(Set bikeReservations) {
        this.bikeReservations = bikeReservations;
    }


}


