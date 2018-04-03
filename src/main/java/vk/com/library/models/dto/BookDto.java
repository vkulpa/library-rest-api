package vk.com.library.models.dto;

import vk.com.library.models.entities.User;

import java.util.Set;

public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private Integer inventory;
    private Boolean available;
    private Set<ReaderDto> readers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Set<ReaderDto> getReaders() {
        return readers;
    }

    public void setReaders(Set<ReaderDto> readers) {
        this.readers = readers;
    }
}
