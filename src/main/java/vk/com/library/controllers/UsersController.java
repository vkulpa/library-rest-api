package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vk.com.library.dto.UserDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.services.api.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UsersController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserDto register(@ModelAttribute("user") @Valid UserDto userDto) {
        return userService.registerNewUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto userByIdOrUsername(@PathVariable final Integer id) {
        Optional<UserDto> user = userService.findById(id);
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.get();
    }
}
