package nl.tudelft.oopp.group43.project.models;

public class Users {
    private String firstName;
    private String lastName;
    private String netId;

    /**
     * constructor the user.
     * @param firstName the first name
     * @param lastName the last name
     * @param netId user's netID
     *
     */
    public Users(String firstName,String lastName,String netId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.netId = netId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNetId() {
        return netId;
    }
}
