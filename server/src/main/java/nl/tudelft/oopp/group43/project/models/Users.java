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
@Table(name = "users")
public class Users {


    @Column(name = "id")
    private Integer id;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
<<<<<<< HEAD
    private String netId;
    private String rules;
    private String type;

    /**
     * constructor the user.
     * @param firstName the first name
     * @param lastName the last name
     * @param netId user's netID
     *
     */
    public Users(String firstName,String lastName,String netId,String rules, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.netId = netId;
        this.rules = rules;
        this.type = type;
=======

    @Column(name = "email")
    private String email;

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
        this.username = username;
        this.hash = Hashing.sha256().hashString(hash, Charsets.UTF_8).toString();
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public boolean hashed_pass(String password) {
        return this.hash.equals(Hashing.sha256().hashString(hash, StandardCharsets.UTF_8).toString());
>>>>>>> 70ae42532f630b72042007eb8582714ef435c823
    }

    public String getUsername() {
        return this.username;
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
