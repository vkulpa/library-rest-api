package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vk.com.library.dto.BookDto;
import vk.com.library.services.api.BookService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/v1/books")
@RestController
public class BooksController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<BookDto> index() {
        return bookService.findAll();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/available")
    public List<BookDto> available() {
        return bookService.findByAvailable(true);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/unavailable")
    public List<BookDto> unavailable() {
        return bookService.findByAvailable(false);
    }

    @GetMapping("/{id}")
    public BookDto show(@PathVariable final Integer id) {
        Optional<BookDto> book = bookService.findById(id);
        book.orElseThrow(() -> new RuntimeException("Book not found"));
        return book.get();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin")
    public List<BookDto> adminIndex() {
        return bookService.findAll();
    }
}
