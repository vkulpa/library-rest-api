package vk.com.library.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.entities.Book;
import vk.com.library.models.entities.Reader;
import vk.com.library.models.entities.User;
import vk.com.library.models.services.api.BookService;
import vk.com.library.repositories.BookRepository;
import vk.com.library.repositories.ReaderRepository;
import vk.com.library.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public List<BasicBookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BasicBookDto> basicBookDtos = books.stream().map(this::convertEntityToBasicDto).collect(Collectors.toList());
        return basicBookDtos;
    }

    @Override
    public Optional<BasicBookDto> findById(Integer id) {
        Optional<Book> entity = bookRepository.findById(id);
        return entity.map(this::convertEntityToBasicDto);
    }

    @Override
    public List<BookDto> booksWithReaders() {
        return null;
    }

    @Override
    public BookDto readersByBook(Integer id) {
        return null;
    }

    @Transactional
    @Override
    public BasicBookDto create(BasicBookDto book) {

        return book;
    }

    @Transactional
    @Override
    public BasicBookDto update(BasicBookDto book) {
        return book;
    }

    @Transactional
    @Override
    public BookDto takeFromLibrary(BasicBookDto bookDto, Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Book> book = bookRepository.findById(bookDto.getId());

        Reader reader = new Reader(book.get(), user.get());
        readerRepository.save(reader);

        book = bookRepository.findById(bookDto.getId());
        Set<Reader> readers = new HashSet<>();
        readers.add(reader);
        return convertEntityToDto(book.get(), readers);
    }

    @Transactional
    @Override
    public BookDto returnToLibrary(BasicBookDto book, Integer user_id) {
        return new BookDto();
    }

    private BasicBookDto convertEntityToBasicDto(Book entity) {
        BasicBookDto dto = new BasicBookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable((entity.getInventory() - entity.getReaders().size()) > 0);
        return dto;
    }

    private BookDto convertEntityToDto(Book entity, Set<Reader> readers) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable((entity.getInventory() - entity.getReaders().size()) > 0);
        dto.setReaders(readers);
        return dto;
    }
}
