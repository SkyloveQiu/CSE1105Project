package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import nl.tudelft.oopp.group43.utilities.GenerateRandomSalt;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;


@Entity
@DynamicUpdate
@Table(name = "user")
public class Users {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "type")
    private String type;

    //empty constructor
    public Users() {
    }

    @JsonCreator
    public Users(@JsonProperty("hash") String hash,
                 @JsonProperty("first_name") String firstName,
                 @JsonProperty("last_name") String lastName,
                 @JsonProperty("email") String email,
                 @JsonProperty("role") String role,
                 @JsonProperty("type") String type) {


        this.salt = GenerateRandomSalt.generateSafeToken();
        this.hash = hash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.type = type;

    }

    public boolean hashed_pass(String password) {
        String copy = new String(salt);
        copy += this.salt;
        return password.equals(Hashing.sha256().hashString(hash + salt, StandardCharsets.UTF_8).toString());
    }

    public String hashed_pass_return_string() {
        String copy = new String(hash);
        copy += this.salt;
        return Hashing.sha256().hashString(hash + salt, StandardCharsets.UTF_8).toString();
    }

    public Integer getId() {
        return id;
    }


    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.hash;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setHash(String salt) {
        this.hash = Hashing.sha256().hashString(hash + salt, StandardCharsets.UTF_8).toString();
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }
}
