package nl.tudelft.oopp.group43.project.payload;

public class JwtRespones {
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private int status;

    public JwtRespones(String token,String username,String role,String firstName,String lastName,int status){
        this.token = token;
        this.username = username;
        this.status = status;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
