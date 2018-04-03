package vk.com.library.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.com.library.exceptions.NotEnoughStockException;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.dto.ReaderDto;
import vk.com.library.models.entities.Book;
import vk.com.library.models.entities.User;
import vk.com.library.models.services.api.BookService;
import vk.com.library.repositories.BookRepository;
import vk.com.library.repositories.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

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
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
    public BasicBookDto takeFromLibrary(BasicBookDto bookDto, Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Book> book = bookRepository.findById(bookDto.getId());
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.get().getReaders().stream().map((r) -> r.getId()).collect(Collectors.toSet()).contains(user.get().getId())) {
            throw new ConstraintViolationException("You own this book", null);
        }

        if (book.get().getAvailability() <= 0) {
            throw new NotEnoughStockException("Book isn't available in Library");
        }

        book.get().getReaders().add(user.get());
        book.get().setReaders(book.get().getReaders());
        Book savedBook = bookRepository.save(book.get());

        return convertEntityToBasicDto(savedBook);
    }

    @Transactional
    @Override
    public BasicBookDto returnToLibrary(BasicBookDto book, Integer user_id) {
        return new BasicBookDto();
    }

    private BasicBookDto convertEntityToBasicDto(Book entity) {
        BasicBookDto dto = new BasicBookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable(entity.getAvailability() > 0);
        return dto;
    }

    private BookDto convertEntityToDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable(entity.getAvailability() > 0);
        dto.setReaders(entity.getReaders().stream().map((u) -> new ReaderDto(u.getId(), u.getUsername())).collect(Collectors.toSet()));
        return dto;
    }
}
