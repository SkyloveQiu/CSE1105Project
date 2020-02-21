package nl.tudelft.oopp.group43.project.service;

public interface SecurityService {
    String findLoggedInEmail();

    void autoLogin(String email, String password);

}
