package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.com.library.dto.UserDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.services.api.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/users", headers = "X-API-VERSION=1", produces = "application/vnd.library.app-v1+json")
public class UsersController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public List<UserDto> index() {
        return userService.findAll();
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        return userService.registerNewUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto userByIdOrUsername(@PathVariable final Integer id) {
        Optional<UserDto> user = userService.findById(id);
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.get();
    }
}
