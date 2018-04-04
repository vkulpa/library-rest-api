package vk.com.library.models.dto;

import org.hibernate.validator.constraints.Range;
import vk.com.library.validations.markers.BookingMarker;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class BookDto {
    @NotNull(groups = UpdateMarker.class)
    private Integer id;

    @NotNull(groups = CreateMarker.class)
    @Size(min = 5, max = 255, groups = { CreateMarker.class, UpdateMarker.class })
    private String name;

    @NotNull(groups = CreateMarker.class)
    @Size(min = 2, max = 255, groups = { CreateMarker.class, UpdateMarker.class })
    private String author;

    @NotNull(groups = UpdateMarker.class)
    @Range(groups = { CreateMarker.class, UpdateMarker.class }, min = 0, max = 100)
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
