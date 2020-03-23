package nl.tudelft.oopp.group43.project.payload;

import nl.tudelft.oopp.group43.project.models.User;

public class UserResponse {
    User user;
    int  status;

    public UserResponse(User user, int status) {
        this.user = user;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
