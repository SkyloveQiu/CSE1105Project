package nl.tudelft.oopp.group43.project.service;

import nl.tudelft.oopp.group43.project.models.User;

public interface EmailService {
    void sendEmail(User user);
}
