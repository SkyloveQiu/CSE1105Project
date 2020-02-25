package nl.tudelft.oopp.group43.project.controllers;


import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.JwtRespones;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import nl.tudelft.oopp.group43.project.service.SecurityService;
import nl.tudelft.oopp.group43.project.service.UserService;
import nl.tudelft.oopp.group43.project.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;



    @Autowired
    private UserRepository repository;

//
//    @GetMapping("/user")
//    @ResponseBody
//    public List<User> getUser(){
//        return repository.findAll();
//
//    }
//
//    @PutMapping("/users/{email}")
//    @ResponseBody
//    public String updateUser(@RequestBody User userWithNewInfo, @PathVariable String email){
//
//        String password = userWithNewInfo.getPassword();
//
//        String salt = repository.getOne(email).getSalt();
//
//        // to do all updates
//        User a = repository.getOne(email);
//        //update current entry with new name
//        a.setLastName("lastName");
//
//        userWithNewInfo.setHash(salt);
//        userWithNewInfo.setSalt(salt);
//
//        if(userWithNewInfo.getPassword().equals(repository.getOne(email).getPassword()))
//        {
//
//            repository.save(a);
//            return "USER UPDATED!";
//        } else {
//            return "WRONG PASSWORD!";
//        }
//    }


//
//
//    @PutMapping("/user")
//    @ResponseBody
//    public String createUser(@RequestBody User newUser){
//        repository.save(newUser);
//        return "NEW USER: " + newUser.getEmail();
//    }

    @PostMapping("/registration")
    public ResponseEntity<JwtRespones> registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        System.out.println("we have receive message!");
        System.out.println(userForm.toString());
//        userForm.setSalt();
//        userForm.setHash(userForm.getSalt());
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.save(userForm);

        User user = securityService.autoLogin(userForm.getUsername(), userForm.getPassword());
        JwtRespones jwtRespones = new JwtRespones(user.getToken(),user.getUsername(),user.getRole());
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);
    }


    @PostMapping("/token")
    public ResponseEntity<JwtRespones> getToken(@RequestParam("username") final String username, @RequestParam("password") final String password) {
        User user = securityService.autoLogin(username,password);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        JwtRespones jwtRespones = new JwtRespones(user.getToken(),user.getUsername(),user.getRole());
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);

    }

}
