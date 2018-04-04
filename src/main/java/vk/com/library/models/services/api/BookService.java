package vk.com.library.models.services.api;

import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.services.LibraryUser;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BasicBookDto> findAll();
    Optional<BasicBookDto> findById(Integer id);
    BookDto create(BookDto book);
    BookDto update(BookDto book);
    BasicBookDto takeFromLibrary(BasicBookDto book, Integer user_id);
    BasicBookDto returnToLibrary(BasicBookDto book, Integer user_idN);
    List<BookDto> booksWithReaders();
    BookDto readersByBook(Integer id);
}
