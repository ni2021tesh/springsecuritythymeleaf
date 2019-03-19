// Copyright (c) 2018 Travelex Ltd

package info.niteshjha.validation;

import info.niteshjha.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidatePassword implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object user, Errors errors) {
        User validateUser = (User) user;
        if (!validateUser.getPassword().equals(validateUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "PasswordNotMatch");
        }
    }
}
