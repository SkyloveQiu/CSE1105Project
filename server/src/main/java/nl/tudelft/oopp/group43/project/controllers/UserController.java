package nl.tudelft.oopp.group43.project.controllers;


import java.util.List;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.payload.JwtRespones;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import nl.tudelft.oopp.group43.project.service.EmailService;
import nl.tudelft.oopp.group43.project.service.SecurityService;
import nl.tudelft.oopp.group43.project.service.TokenService;
import nl.tudelft.oopp.group43.project.service.UserService;
import nl.tudelft.oopp.group43.project.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

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


    @GetMapping("/mail")
    public void sendEmail() {
        emailService.sendEmail("ziang.qiu@gmail.com");
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        System.out.println("we have receive message!");
        System.out.println(userForm.toString());
//        userForm.setSalt();
//        userForm.setHash(userForm.getSalt());
        userValidator.validate(userForm, bindingResult);
        String password = userForm.getPassword();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError index : list){
                message.append(index.getDefaultMessage());
            }
            ErrorResponse errorResponse = new ErrorResponse("registration fail",message.toString(),HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
        }

        userService.save(userForm);
        tokenService.save(userForm);
        User user = securityService.autoLogin(userForm.getUsername(), password);
        JwtRespones jwtRespones = new JwtRespones(user.getToken(),user.getUsername(),user.getRole(),user.getFirstName(),user.getLastName(),HttpStatus.OK.value());
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);
    }


    @PostMapping("/token")
    public ResponseEntity getToken(@RequestParam("username") final String username, @RequestParam("password") final String password) {
        User user = securityService.autoLogin(username,password);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse("auth fail","username or password wrong",HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
        }
        JwtRespones jwtRespones = new JwtRespones(user.getToken(),user.getUsername(),user.getRole(),user.getFirstName(),user.getLastName(),HttpStatus.OK.value());
        tokenService.save(user);
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);

    }

}
