package nl.tudelft.oopp.group43.project.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.payload.JwtRespones;
import nl.tudelft.oopp.group43.project.payload.UserResponse;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
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

    /**
     * post message to set up a new account.
     *
     * @param userForm      the user form.
     * @param bindingResult the result of the verification system.
     * @param model         the model of the user.
     * @return the result of registration.
     */
    @PostMapping("/registration")
    public ResponseEntity registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        System.out.println("we have receive message!");
        System.out.println(userForm.toString());
        userValidator.validate(userForm, bindingResult);
        final String password = userForm.getPassword();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError index : list) {
                message.append(index.getDefaultMessage());
            }
            ErrorResponse errorResponse = new ErrorResponse("registration fail", message.toString(), HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        userService.save(userForm);
        User user = securityService.autoLogin(userForm.getUsername(), password);
        tokenService.save(userForm);
        JwtRespones jwtRespones = new JwtRespones(user.getToken(), user.getUsername(), user.getRole(), user.getFirstName(), user.getLastName(), HttpStatus.OK.value());
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);
    }

    /**
     * login method, use http request to get token.
     *
     * @param username the username user want to use to login.
     * @param password the password user want to login.
     * @return the result of the login system.
     */
    @PostMapping("/token")
    public ResponseEntity getToken(@RequestParam("username") final String username, @RequestParam("password") final String password) throws UnsupportedEncodingException {
        String usernameDecoded = utf8DecodeValue(username);
        String passwordDecoded = utf8DecodeValue(password);

        User user = securityService.autoLogin(usernameDecoded, passwordDecoded);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse("auth fail", "username or password wrong", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        JwtRespones jwtRespones = new JwtRespones(user.getToken(), user.getUsername(), user.getRole(), user.getFirstName(), user.getLastName(), HttpStatus.OK.value());
        tokenService.save(user);
        return new ResponseEntity<>(jwtRespones, HttpStatus.OK);

    }

    /**
     * find the user info by the user token.
     * @param token the token of the user.
     * @return the result of the request.
     */
    @PostMapping("/name")
    public ResponseEntity getName(@RequestParam("token") final String token) {
        User user = userService.findByToken(token);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse("error","can not find user, please get token again", HttpStatus.FORBIDDEN.value());
            return new  ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
        }
        UserResponse userResponse = new UserResponse(user,HttpStatus.OK.value());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping("/api/hello")
    public String get() {
        return "ok";
    }


    /**
     * Decodes a utf8 encoded value.
     * @param value utf8-encoded string value
     * @return decoded string value
     */
    private String utf8DecodeValue(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
    }
}
