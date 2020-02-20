package nl.tudelft.oopp.group43.project.controllers;

import nl.tudelft.oopp.group43.project.models.Users;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@EnableJpaRepositories("nl.tudelft.oopp.group43.project.repositories")
@RestController
public class UserController {

    @Autowired
    private UserRepository repository;


    @GetMapping("/user")
    @ResponseBody
    public List<Users> getUser(){
        System.out.println("gog");
        return repository.findAll();
    }

    @PutMapping("/user/{username}")
    @ResponseBody
    public String updateUser(@RequestBody Users userWithNewInfo, @PathVariable String username){
        String password = userWithNewInfo.getPassword();
        if(password.equals(repository.getOne(username).getPassword())){
            repository.save(userWithNewInfo);
            return "USER UPDATED!";
        } else {
            return "WRONG PASSWORD!";
        }
    }

    @PutMapping("/user")
    @ResponseBody
    public String createUser(@RequestBody Users newUser){
        repository.save(newUser);
        return "NEW USER: " + newUser.getEmail();
    }


}
