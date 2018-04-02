package vk.com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.com.library.models.entities.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    Reader findByBookAndUser();
}
