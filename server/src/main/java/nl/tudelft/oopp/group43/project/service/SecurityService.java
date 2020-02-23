package nl.tudelft.oopp.group43.project.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
