package vk.com.library.services.api;

import vk.com.library.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    List<BookDto> findByAvailable(boolean available);
    Optional<BookDto> findById(Integer id);
}
