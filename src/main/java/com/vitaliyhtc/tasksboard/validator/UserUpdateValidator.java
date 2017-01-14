package com.vitaliyhtc.tasksboard.validator;

import com.vitaliyhtc.tasksboard.model.User;
import com.vitaliyhtc.tasksboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link User} class due update process,
 * implements {@link Validator} interface.
 *
 * @author VitaliyRozumyak
 * @version 1.0
 */

@Controller
public class UserUpdateValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if(user.getUsername().length() < 8 || user.getUsername().length() > 32){
            errors.rejectValue("username", "Size.userForm.username");
        }
        User duplicateUser = userService.findByUsername(user.getUsername());
        if(duplicateUser!=null){
            if(duplicateUser.getId()!=user.getId())
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        if(!user.getPassword().isEmpty() || !user.getConfirmPassword().isEmpty()){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
            if(user.getPassword().length()<8 || user.getPassword().length()>127){
                errors.rejectValue("password", "Size.userForm.password");
            }
            if(!user.getConfirmPassword().equals(user.getPassword())){
                errors.rejectValue("confirmPassword", "Different.userForm.password");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if(user.getEmail().length() < 8 || user.getEmail().length() > 254){
            errors.rejectValue("email", "Size.userForm.useremail");
        }
        User duplicateEmailUser = userService.findByEmail(user.getEmail());
        if(duplicateEmailUser!=null){
            if(duplicateEmailUser.getId()!=user.getId()){
                errors.rejectValue("email", "Duplicate.userForm.useremail");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enabled", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNonLocked", "Required");
    }
}
