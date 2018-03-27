package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.com.library.dto.BookDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.services.api.BookService;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/v1/books", headers = "X-API-VERSION=1", produces = "application/vnd.library.app-v1+json")
@RestController
public class BooksController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> index() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto show(@PathVariable final Integer id) {
        Optional<BookDto> book = bookService.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return book.get();
    }
}
