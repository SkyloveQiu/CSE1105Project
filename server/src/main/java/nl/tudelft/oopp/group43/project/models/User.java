package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "user")
public class User {

    @Id
    @Column(name = "email")
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "role")
    private String role;

    @Column(name = "token")
    private String token;
//    @Column(name = "salt")
//    private String salt;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ManyToMany
    private Set<Role> roles;

    //empty constructor
    public User() {
    }

    @JsonCreator
    public User(@JsonProperty("password") String password,
                @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName,
                @JsonProperty("username") String username,
                @JsonProperty("role") String role
                 ) {


//        this.salt = GenerateRandomSalt.generateSafeToken();
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;


    }

//    public boolean hashed_pass(String password) {
//        String copy = new String(salt);
//        copy += this.salt;
//        return password.equals(Hashing.sha256().hashString(password + salt, StandardCharsets.UTF_8).toString());
//    }
//
//    public String hashed_pass_return_string() {
//        String copy = new String(password);
//        copy += this.salt;
//        return Hashing.sha256().hashString(password + salt, StandardCharsets.UTF_8).toString();
//    }




    public String getUsername() {
        return this.username;
    }



    public String getPassword() {
        return password;
    }

//    public String getSalt() {
//        return salt;
//    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public void setHash(String salt) {
//        this.password = this.password + salt;
//    }


    public void setEncriptedHash(String actualpass) {
        this.password = actualpass;
    }

//    public void setSalt() {
//        this.salt = GenerateRandomSalt.generateSafeToken();
//    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return  getPassword().equals(user.getPassword()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getUsername().equals(user.getUsername()) &&
                getRole().equals(user.getRole());
    }


    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
