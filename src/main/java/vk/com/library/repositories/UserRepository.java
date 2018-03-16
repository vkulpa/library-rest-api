package vk.com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.com.library.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
