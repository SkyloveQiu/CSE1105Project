package nl.tudelft.oopp.group43.project.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "user")
public class Users {

    @Id
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
    public Users(@JsonProperty("id") Integer id,
                 @JsonProperty("username") String username,
                 @JsonProperty("hash") String hash,
                 @JsonProperty("salt") String salt,
                 @JsonProperty("first_name") String firstName,
                 @JsonProperty("last_name") String lastName,
                 @JsonProperty("email") String email) {
        this.id = id;
        this.hash = Hashing.sha256().hashString(hash, Charsets.UTF_8).toString();
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public boolean hashed_pass(String password) {
        return this.hash.equals(Hashing.sha256().hashString(hash, StandardCharsets.UTF_8).toString());
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.hash;
    }


    public Integer getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;


    }
}
