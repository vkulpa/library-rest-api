package vk.com.library.services.api;

import vk.com.library.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> findById(Integer id);
    List<UserDto> findAll();
    UserDto registerNewUser(UserDto user);
    UserDto updateUser(UserDto user);
    UserDto updatePassword(UserDto user);
}
