package vk.com.library.models.services.api;

import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BasicBookDto> findAll();
    Optional<BasicBookDto> findById(Integer id);
    BasicBookDto create(BasicBookDto book);
    BasicBookDto update(BasicBookDto book);
    BasicBookDto takeFromLibrary(BasicBookDto book);
    BasicBookDto returnToLibrary(BasicBookDto book);
    List<BookDto> booksWithReaders();
    BookDto readersByBook(Integer id);
}
