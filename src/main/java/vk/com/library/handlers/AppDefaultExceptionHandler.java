package vk.com.library.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vk.com.library.models.dto.ErrorDto;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AppDefaultExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto simpleError(Exception exception) {
        return new ErrorDto();
    }
}
