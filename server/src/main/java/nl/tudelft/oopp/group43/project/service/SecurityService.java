package nl.tudelft.oopp.group43.project.service;

import nl.tudelft.oopp.group43.project.models.User;

public interface SecurityService {
    String findLoggedInUsername();

    User autoLogin(String username, String password);


}
