package vk.com.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vk.com.library.exceptions.ResourceNotFoundException;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.services.LibraryUser;
import vk.com.library.models.services.api.BookService;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import java.security.Principal;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/authors")
    public Map<String, List<BasicBookDto>> byAuthors() {
        return bookService.findAllGroupByAuthor();
    }

    @GetMapping("/{id}")
    public BasicBookDto show(@PathVariable final Integer id) {
        Optional<BasicBookDto> book = bookService.findById(id);
        book.orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return book.get();
    }

    @PostMapping
    public BookDto create(@RequestBody @Validated(CreateMarker.class) BookDto book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable final Integer id, @RequestBody @Validated(UpdateMarker.class) BookDto book) {
        return bookService.update(book);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/booking")
    public BasicBookDto take(@PathVariable final Integer id, Principal principal) {
        return bookService.takeFromLibrary(id, ((LibraryUser)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUserId());
    }

    @DeleteMapping("/{id}/booking")
    public BasicBookDto returnABook(@PathVariable final Integer id, Principal principal) {
        return bookService.returnToLibrary(id, ((LibraryUser)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUserId());
    }

    @PostMapping("/{id}/admin/booking/for_user/{user_id}")
    public BasicBookDto takeByUser(@PathVariable final Integer id, @PathVariable final Integer user_id) {
        return bookService.takeFromLibrary(id, user_id);
    }

    @DeleteMapping("/{id}/admin/booking/for_user/{user_id}")
    public BasicBookDto returnByUser(@PathVariable final Integer id, @PathVariable final Integer user_id) {
        return bookService.returnToLibrary(id, user_id);
    }

    @GetMapping("/admin/readers")
    public List<BookDto> readers() {
        return bookService.booksWithReaders();
    }

    @GetMapping("/{id}/admin/readers")
    public BookDto readersByBook(@PathVariable final Integer id) {
        return bookService.readersByBook(id);
    }
}
