package vk.com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.com.library.entities.UserRole;

import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Set<UserRole> findAllByName(String name);
}
