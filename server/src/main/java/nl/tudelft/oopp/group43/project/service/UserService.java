package nl.tudelft.oopp.group43.project.service;


import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    UserDetails findByToken(String token);
}
