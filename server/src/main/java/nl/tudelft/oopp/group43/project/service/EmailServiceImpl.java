package nl.tudelft.oopp.group43.project.service;

import nl.tudelft.oopp.group43.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getUsername());
        msg.setFrom(from); // <--- THIS IS IMPORTANT

        msg.setSubject("Testing from Spring Boot");
        msg.setText("hello " + user.getLastName()
                + " \n  Thank you for your registration. Your account name is"
                + user.getUsername()
                + " , if you have any problems please contact Group43OOPP@hotmail.com!\n"
                + "CSE1105 Group 43");

        javaMailSender.send(msg);
    }
}
