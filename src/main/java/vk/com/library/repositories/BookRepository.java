package vk.com.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vk.com.library.models.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
