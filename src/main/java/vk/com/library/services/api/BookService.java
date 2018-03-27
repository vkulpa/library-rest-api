package vk.com.library.services.api;

import vk.com.library.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    Optional<BookDto> findById(Integer id);
}
