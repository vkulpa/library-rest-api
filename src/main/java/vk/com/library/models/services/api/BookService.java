package vk.com.library.models.services.api;

import vk.com.library.models.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    Optional<BookDto> findById(Integer id);
}
