package vk.com.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vk.com.library.dto.UserDto;
import vk.com.library.entities.User;
import vk.com.library.entities.UserRole;
import vk.com.library.exceptions.UsernameExistsException;
import vk.com.library.repositories.UserRepository;
import vk.com.library.services.api.UserService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDto> findById(Integer id) {
        Optional<User> entity = userRepository.findById(id);
        return entity.map(this::convertEntityToDto);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Transactional
    public UserDto registerNewUser(UserDto user) throws UsernameExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameExistsException("Username already exists, please choose another one.");
        }
        return convertEntityToDto(userRepository.save(convertDtoToEntity(user)));
    }

    private User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(1, "User"));
        user.setRoles(userRoles);
        return user;
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setActive(user.getActive());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
