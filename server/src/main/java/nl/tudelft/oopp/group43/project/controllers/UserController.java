package nl.tudelft.oopp.group43.project.controllers;


import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import nl.tudelft.oopp.group43.project.service.SecurityService;
import nl.tudelft.oopp.group43.project.service.UserService;
import nl.tudelft.oopp.group43.project.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
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


    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("hello");
        model.addAttribute("userForm", new User());

        return "registration";
    }

//
//
//    @PutMapping("/user")
//    @ResponseBody
//    public String createUser(@RequestBody User newUser){
//        repository.save(newUser);
//        return "NEW USER: " + newUser.getEmail();
//    }

    @PostMapping("/registration")
    public String registration(@RequestBody User userForm, BindingResult bindingResult) {
        System.out.println("we have recieve message!");
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getPassword());

        return "redirect:/welcome";
    }
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }


}
