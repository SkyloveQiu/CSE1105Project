package nl.tudelft.oopp.group43.project.payload;

public class JwtRespones {
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
    private String firstName;
    private String lastName;

    public JwtRespones(String token,String username,String role,String firstName,String lastName){
        this.token = token;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
