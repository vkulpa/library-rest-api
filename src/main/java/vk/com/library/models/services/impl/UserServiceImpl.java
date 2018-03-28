package vk.com.library.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.exceptions.UsernameExistsException;
import vk.com.library.models.dto.UserDto;
import vk.com.library.models.entities.User;
import vk.com.library.models.entities.UserRole;
import vk.com.library.models.services.api.UserService;
import vk.com.library.repositories.UserRepository;
import vk.com.library.repositories.UserRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
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

        User record = new User();
        record.setUsername(user.getUsername());
        record.setPassword(passwordEncoder.encode(user.getPassword()));
        record.setActive(true);
        record.setRoles(userRoleRepository.findAllByName("User"));

        return convertEntityToDto(userRepository.save(record));
    }

    @Transactional
    public UserDto updateUser(UserDto user) throws UsernameNotFoundException, ResourceNotFoundException {
        Optional<User> record = userRepository.findByUsername(user.getUsername());
        if (record.isPresent() && record.get().getId() != user.getId()) {
            throw new UsernameExistsException("Username already exists, please choose another one.");
        }
        record.get().setUsername(user.getUsername());
        record.get().setActive(user.getActive());
        return convertEntityToDto(userRepository.save(record.get()));
    }

    @Transactional
    public UserDto updatePassword(UserDto user) throws ResourceNotFoundException {
        Optional<User> record = userRepository.findById(user.getId());
        record.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        record.get().setPassword(passwordEncoder.encode(user.getPassword()));
        return convertEntityToDto(userRepository.save(record.get()));
    }

    @Transactional
    public UserDto setRoles(UserDto user) throws ResourceNotFoundException {
        Optional<User> record = userRepository.findById(user.getId());
        record.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<String> roleNames = user.getRoles().stream().map(ur -> ur.getName()).collect(Collectors.toSet());
        Set<UserRole> userRoles = userRoleRepository.findAllByNames(roleNames);
        record.get().setRoles(userRoles);

        return convertEntityToDto(userRepository.save(record.get()));
    }

    private User getUserById(UserDto user) {
        Optional<User> record = userRepository.findById(user.getId());
        record.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return record.get();
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
