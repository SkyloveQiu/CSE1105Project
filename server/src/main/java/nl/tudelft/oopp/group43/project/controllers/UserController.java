package nl.tudelft.oopp.group43.project.controllers;


import nl.tudelft.oopp.group43.project.models.Users;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;


    @GetMapping("/user")
    @ResponseBody
    public List<Users> getUser(){
        return repository.findAll();
    }

    @PutMapping("/users/{email}")
    @ResponseBody
    public String updateUser(@RequestBody Users userWithNewInfo, @PathVariable String email){

        String password = userWithNewInfo.getPassword();

        String salt = repository.getOne(email).getSalt();

        // to do all updates
        Users a = repository.getOne(email);
        //update current entry with new name
        a.setLastName("lastNamee");

        userWithNewInfo.setHash(salt);
        userWithNewInfo.setSalt(salt);

        if(userWithNewInfo.getHash().equals(repository.getOne(email).getPassword()))
        {

            repository.save(a);
            return "USER UPDATED!";
        } else {
            return "WRONG PASSWORD!";
        }
    }

    @PutMapping("/user")
    @ResponseBody
    public String createUser(@RequestBody Users newUser){
        newUser.setHash(newUser.getSalt());
        repository.save(newUser);
        return "NEW USER: " + newUser.getEmail();
    }


}
