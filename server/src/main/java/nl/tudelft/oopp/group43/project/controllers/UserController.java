package nl.tudelft.oopp.group43.project.controllers;


import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;


    @GetMapping("/user")
    @ResponseBody
    public List<User> getUser(){
        return repository.findAll();

    }

    @PutMapping("/users/{email}")
    @ResponseBody
    public String updateUser(@RequestBody User userWithNewInfo, @PathVariable String email){

        String password = userWithNewInfo.getPassword();

        String salt = repository.getOne(email).getSalt();

        // to do all updates
        User a = repository.getOne(email);
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
    public String createUser(@RequestBody User newUser){
        newUser.setHash(newUser.getSalt());
        repository.save(newUser);
        return "NEW USER: " + newUser.getEmail();
    }


}
