package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String greeting(@RequestParam(value = "firstName", defaultValue = "Andrew") String firstName,@RequestParam(value = "lastName", defaultValue = "Andrew")String lastname,@RequestParam(value = "netID", defaultValue = "unknown")String netID) {
        return "hello";
    }

}
