package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.services.api.BookService;
import vk.com.library.validations.markers.BookingMarker;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/v1/books", headers = "X-API-VERSION=1", produces = "application/vnd.library.app-v1+json")
@RestController
public class BooksController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BasicBookDto> index() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BasicBookDto show(@PathVariable final Integer id) {
        Optional<BasicBookDto> book = bookService.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return book.get();
    }

    @PostMapping("/{id}/take")
    @PreAuthorize("#id == book.id")
    public BasicBookDto take(@RequestBody @Validated(BookingMarker.class) BasicBookDto book) {
        return bookService.takeFromLibrary(book);
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("#id == book.id")
    public BasicBookDto returnABook(@RequestBody @Validated(BookingMarker.class) BasicBookDto book) {
        return bookService.returnToLibrary(book);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/readers")
    public List<BookDto> readers() {
        return bookService.booksWithReaders();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/{id}/readers")
    public BookDto readersByBook(@PathVariable final Integer id) {
        return bookService.readersByBook(id);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/create")
    public BasicBookDto create(@RequestBody @Validated(CreateMarker.class) BasicBookDto book) {
        return bookService.create(book);
    }

    @PreAuthorize("#id == book.id && hasRole('Admin')")
    @PutMapping("/{id}/update")
    public BasicBookDto update(@RequestBody @Validated(UpdateMarker.class) BasicBookDto book) {
        return bookService.update(book);
    }
}
