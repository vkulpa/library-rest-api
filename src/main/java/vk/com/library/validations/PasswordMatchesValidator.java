package vk.com.library.validations;

import vk.com.library.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {
    @Override
    public void initialize(final PasswordMatches annotation) {}

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (userDto.getPassword() != null && userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            isValid = true;
        } else {

        }

        return isValid;
    }
}
