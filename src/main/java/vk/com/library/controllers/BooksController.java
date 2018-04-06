package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.services.LibraryUser;
import vk.com.library.models.services.api.BookService;
import vk.com.library.validations.markers.BookingMarker;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import java.security.Principal;
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
    @PreAuthorize("#id == #book.id")
    public BasicBookDto take(@PathVariable final Integer id, @RequestBody @Validated(BookingMarker.class) BasicBookDto book, Principal principal) {
        return bookService.takeFromLibrary(book, ((LibraryUser)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUserId());
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("#id == #book.id")
    public BasicBookDto returnABook(@PathVariable final Integer id, @RequestBody @Validated(BookingMarker.class) BasicBookDto book, Principal principal) {
        return bookService.returnToLibrary(book, ((LibraryUser)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUserId());
    }

    @PreAuthorize("hasRole('Admin') && #id == #book.id")
    @PostMapping("/{id}/take/{user_id}")
    public BasicBookDto takeByUser(@PathVariable final Integer id, @PathVariable final Integer user_id, @RequestBody @Validated(BookingMarker.class) BasicBookDto book) {
        return bookService.takeFromLibrary(book, user_id);
    }

    @PreAuthorize("hasRole('Admin') && #id == #book.id")
    @PostMapping("/{id}/return/{user_id}")
    public BasicBookDto returnByUser(@PathVariable final Integer id, @PathVariable final Integer user_id, @RequestBody @Validated(BookingMarker.class) BasicBookDto book) {
        return bookService.returnToLibrary(book, user_id);
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
    @PostMapping
    public BookDto create(@RequestBody @Validated(CreateMarker.class) BookDto book) {
        return bookService.create(book);
    }

    @PreAuthorize("hasRole('Admin') && #id == #book.id")
    @PutMapping("/{id}/update")
    public BookDto update(@PathVariable final Integer id, @RequestBody @Validated(UpdateMarker.class) BookDto book) {
        return bookService.update(book);
    }
}
