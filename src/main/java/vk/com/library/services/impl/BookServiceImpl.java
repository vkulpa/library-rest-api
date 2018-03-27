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
        List<BookDto> bookDtos = books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public Optional<BookDto> findById(Integer id) {
        Optional<Book> entity = bookRepository.findById(id);
        return entity.map(this::convertEntityToDto);
    }

    private BookDto convertEntityToDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        return dto;
    }
}
