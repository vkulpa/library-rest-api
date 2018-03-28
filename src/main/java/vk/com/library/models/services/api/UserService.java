package vk.com.library.models.services.api;

import vk.com.library.models.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> findById(Integer id);
    List<UserDto> findAll();
    UserDto registerNewUser(UserDto user);
    UserDto updateUser(UserDto user);
    UserDto updatePassword(UserDto user);
    UserDto setRoles(UserDto user);
}
