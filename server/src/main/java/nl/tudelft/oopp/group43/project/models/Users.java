package nl.tudelft.oopp.group43.project.models;

public class Users {
    private String firstName;
    private String lastName;
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
