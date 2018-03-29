package vk.com.library.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.com.library.models.dto.BasicBookDto;
import vk.com.library.models.dto.BookDto;
import vk.com.library.models.entities.Book;
import vk.com.library.repositories.BookRepository;
import vk.com.library.models.services.api.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BasicBookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        List<BasicBookDto> basicBookDtos = books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        return basicBookDtos;
    }

    @Override
    public Optional<BasicBookDto> findById(Integer id) {
        Optional<Book> entity = bookRepository.findById(id);
        return entity.map(this::convertEntityToDto);
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
    public BasicBookDto takeFromLibrary(BasicBookDto book) {
        return book;
    }

    @Transactional
    @Override
    public BasicBookDto returnToLibrary(BasicBookDto book) {
        return book;
    }

    private BasicBookDto convertEntityToDto(Book entity) {
        BasicBookDto dto = new BasicBookDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setAvailable((entity.getInventory() - entity.getReaders().size()) > 0);
        return dto;
    }
}
