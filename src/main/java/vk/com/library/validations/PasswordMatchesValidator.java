package vk.com.library.validations;

import vk.com.library.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        UserDto user = (UserDto)object;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
