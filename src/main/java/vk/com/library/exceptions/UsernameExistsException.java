package vk.com.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String msg) {
        super(msg);
    }

    public UsernameExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
