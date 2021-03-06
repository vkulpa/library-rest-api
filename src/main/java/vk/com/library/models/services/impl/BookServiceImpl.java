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
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Map<String, List<BasicBookDto>> findAllGroupByAuthor() {
        List<Book>books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToBasicDto).collect(Collectors.groupingBy(BasicBookDto::getAuthor));
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
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return convertEntityToDto(book.get());
    }

    @Transactional
    @Override
    public BookDto create(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setName(bookDto.getName());
        book.setInventory(bookDto.getInventory());
        book = bookRepository.save(book);
        return convertEntityToDto(book);
    }

    @Transactional
    @Override
    public BookDto update(BookDto bookDto) {
        Optional<Book> book = bookRepository.findById(bookDto.getId());
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (bookDto.getName() != null) {
            book.get().setName(bookDto.getName());
        }
        if (bookDto.getAuthor() != null) {
            book.get().setAuthor(bookDto.getAuthor());
        }
        book.get().setInventory(bookDto.getInventory());

        return convertEntityToDto(bookRepository.save(book.get()));
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public BasicBookDto takeFromLibrary(Integer id, Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (book.get().getReaders().stream().map(User::getId).collect(Collectors.toSet()).contains(user.get().getId())) {
            throw new ConstraintViolationException("You have taken this book already", null);
        }

        if (getAvailability(book.get()) <= 0) {
            throw new NotEnoughStockException("Book isn't available in Library");
        }

        book.get().getReaders().add(user.get());
        book.get().setReaders(book.get().getReaders());
        Book savedBook = bookRepository.save(book.get());

        return convertEntityToBasicDto(savedBook);
    }

    @Transactional
    @Override
    public BasicBookDto returnToLibrary(Integer id, Integer user_id) {
        Optional<User> user = userRepository.findById(user_id);
        Optional<Book> book = bookRepository.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        user.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!book.get().getReaders().stream().map(User::getId).collect(Collectors.toSet()).contains(user.get().getId())) {
            throw new ConstraintViolationException("You haven't taken this book", null);
        }

        book.get().getReaders().remove(user.get());
        bookRepository.save(book.get());

        return convertEntityToBasicDto(book.get());
    }

    private BasicBookDto convertEntityToBasicDto(Book entity) {
        BasicBookDto dto = new BasicBookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable(getAvailability(entity) > 0);
        return dto;
    }

    private BookDto convertEntityToDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setInventory(entity.getInventory());
        dto.setAvailable(getAvailability(entity) > 0);
        dto.setReaders(entity.getReaders() != null ? entity.getReaders().stream().map((u) -> new ReaderDto(u.getId(), u.getUsername())).collect(Collectors.toSet()) : null);
        return dto;
    }

    private Integer getAvailability(Book entity) {
        return entity.getInventory() - entity.getReaders().size();
    }
}
