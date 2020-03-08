package nl.tudelft.oopp.group43.project.controllers;

import javax.validation.constraints.NotNull;

public class UserSource {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public UserSource(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
