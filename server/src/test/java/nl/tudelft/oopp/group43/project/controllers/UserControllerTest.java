package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import nl.tudelft.oopp.group43.project.service.SecurityService;
import nl.tudelft.oopp.group43.project.service.TokenService;
import nl.tudelft.oopp.group43.project.service.UserService;
import nl.tudelft.oopp.group43.project.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

public class UserControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private SecurityService mockSecurityService;
    @Mock
    private UserValidator mockUserValidator;
    @Mock
    private TokenService mockTokenService;
    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private UserController userControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    //    @Test
    //    public void testRegistration() {
    //        final User userForm = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        final BindingResult bindingResult = null;
    //        final Model model = null;
    //
    //        final User user = new User("email1", "firstName", "lastName", "password", "role", "token1", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockSecurityService.autoLogin("username", "password")).thenReturn(user);
    //
    //        final ResponseEntity result = userControllerUnderTest.registration(userForm, bindingResult, model);
    //
    //        verify(mockUserValidator).validate(any(Object.class), any(Errors.class));
    //        verify(mockUserService).save(any(User.class));
    //        verify(mockTokenService).save(any(User.class));
    //    }

    @Test
    public void testGetToken() throws Exception {
        // Setup

        // Configure SecurityService.autoLogin(...).
        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockSecurityService.autoLogin("username", "password")).thenReturn(user);

        // Run the test
        final ResponseEntity result = userControllerUnderTest.getToken("username", "password");

        // Verify the results
        verify(mockTokenService).save(any(User.class));
    }

    //    @Test(expected = UnsupportedEncodingException.class)
    //    public void testGetToken_ThrowsUnsupportedEncodingException() throws Exception {
    //        Assertions.assertThrows(UnsupportedEncodingException.class, () -> {
    //            final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //            when(mockSecurityService.autoLogin("username", "password")).thenReturn(user);
    //
    //            userControllerUnderTest.getToken("username", "password");
    //        });
    //    }

    @Test
    public void testGetName() {
        // Setup

        // Configure UserService.findByToken(...).
        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserService.findByToken("token")).thenReturn(user);

        // Run the test
        final ResponseEntity result = userControllerUnderTest.getName("token");

        // Verify the results
    }

    @Test
    public void testGet() {
        // Setup

        // Run the test
        final String result = userControllerUnderTest.get();

        // Verify the results
        assertNotNull(result);
    }

    //    @Test
    //    public void testChangePassword() throws Exception {
    //
    //        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockRepository.findUserByToken("token")).thenReturn(user);
    //
    //
    //        final User user1 = new User("email1", "firstName", "lastName", "password", "role", "token1", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockSecurityService.autoLogin("username", "password")).thenReturn(user1);
    //
    //        // Run the test
    //        final ResponseEntity result = userControllerUnderTest.changePassword("oldPassword", "newPassword", "token");
    //
    //        // Verify the results
    //        verify(mockUserService).save(any(User.class));
    //    }

    //    @Test(expected = UnsupportedEncodingException.class)
    //    public void testChangePassword_ThrowsUnsupportedEncodingException() throws Exception {
    //        // Setup
    //
    //        // Configure UserRepository.findUserByToken(...).
    //        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockRepository.findUserByToken("token")).thenReturn(user);
    //
    //        // Configure SecurityService.autoLogin(...).
    //        final User user1 = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockSecurityService.autoLogin("username", "password")).thenReturn(user1);
    //
    //        // Run the test
    //        userControllerUnderTest.changePassword("oldPassword", "newPassword", "token");
    //    }
}
