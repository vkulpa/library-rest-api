package vk.com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.com.library.models.entities.UserRole;

import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Set<UserRole> findAllByName(String name);
    @Query(value = "SELECT * FROM roles WHERE name IN :names", nativeQuery = true)
    Set<UserRole> findAllByNames(@Param("names") Set<String> names);
}
