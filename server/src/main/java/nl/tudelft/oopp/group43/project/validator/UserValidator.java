package nl.tudelft.oopp.group43.project.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aclass) {
        return User.class.equals(aclass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        String email = user.getUsername();
        if (isValidEmailAddress(email) == false) {
            errors.rejectValue("username", "not a valid email address");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "one email address can only register one account.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 100) {
            errors.rejectValue("password", "Size.userForm.password");
        }


    }

    /**
     * check the email is valid address or not.
     * @param email the email address.
     * @return the result of the check.
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
