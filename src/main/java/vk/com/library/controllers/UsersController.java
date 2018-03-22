package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vk.com.library.dto.UserDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.services.LibraryUserDetails;
import vk.com.library.services.api.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/users", headers = "X-API-VERSION=1", produces = "application/vnd.library.app-v1+json")
public class UsersController {
    @Autowired
    LibraryUserDetails libraryUserDetails;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Validated UserDto userDto) {
        return userService.registerNewUser(userDto);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public List<UserDto> index() {
        return userService.findAll();
    }

    @PreAuthorize("hasPermission('User', 'write')")
    @GetMapping("/{id}")
    public UserDto userById(@PathVariable final Integer id) {
        Optional<UserDto> user = userService.findById(id);
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.get();
    }
}
