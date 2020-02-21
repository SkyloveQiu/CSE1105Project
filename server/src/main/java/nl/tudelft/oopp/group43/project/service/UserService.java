package nl.tudelft.oopp.group43.project.service;


import nl.tudelft.oopp.group43.project.models.User;

public interface UserService {
    void save(User user);

    User findByEmail(String email);
}
