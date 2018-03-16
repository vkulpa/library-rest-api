package vk.com.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.com.library.dto.BookDto;
import vk.com.library.entities.Book;
import vk.com.library.repositories.BookRepository;
import vk.com.library.services.api.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream().map(this::convertEntiyToDto).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public List<BookDto> findByAvailable(boolean available) {
        List<Book> books = bookRepository.findByAvailable(available);
        List<BookDto> bookDtos = books.stream().map(this::convertEntiyToDto).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public Optional<BookDto> findById(Integer id) {
        Optional<Book> entity = bookRepository.findById(id);
        Optional<BookDto> dto;

        if (entity.isPresent()) {
            Book record = entity.get();
            dto = Optional.of(convertEntiyToDto(record));
        } else {
            dto = Optional.empty();
        }

        return dto;
    }

    private BookDto convertEntiyToDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable(entity.getAvailable());
        return dto;
    }
}
