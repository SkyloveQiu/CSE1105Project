package nl.tudelft.oopp.group43.project.service;

import nl.tudelft.oopp.group43.project.models.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User findByToken(String token);
}
