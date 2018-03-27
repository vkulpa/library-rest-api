package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vk.com.library.dto.UserDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.services.LibraryUserDetails;
import vk.com.library.services.api.UserService;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;
import vk.com.library.validations.markers.UpdatePasswordMarker;

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
    public UserDto register(@RequestBody @Validated(CreateMarker.class) UserDto userDto) {
        return userService.registerNewUser(userDto);
    }

    @PreAuthorize("hasPermission(#id, 'User', 'read')")
    @GetMapping("/{id}")
    public UserDto userById(@PathVariable final Integer id) {
        Optional<UserDto> user = userService.findById(id);
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.get();
    }

    @PreAuthorize("#id == #userDto.id && hasPermission(#id, 'User', 'write')")
    @PutMapping("/{id}/update")
    public UserDto update(@PathVariable final Integer id,
                          @RequestBody @Validated(UpdateMarker.class) UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PreAuthorize("#id == #userDto.id && hasPermission(#id, 'User', 'write')")
    @PutMapping("/{id}/updatePassword")
    public UserDto updatePassword(@PathVariable final Integer id,
                                  @RequestBody @Validated(UpdatePasswordMarker.class) UserDto userDto) {
        return userService.updatePassword(userDto);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{id}/updateRole")
    public UserDto updateRole(@PathVariable final Integer id,
                              @RequestBody @Validated UserDto userDto) {
        return userDto;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public List<UserDto> index() {
        return userService.findAll();
    }
}
