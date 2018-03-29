package vk.com.library.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vk.com.library.validations.markers.BookingMarker;
import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(value = {"inventory"}, allowSetters = true)
public class BasicBookDto {
    @NotNull(groups = { UpdateMarker.class, BookingMarker.class })
    private Integer id;

    @NotNull(groups = CreateMarker.class)
    @Size(min = 5, max = 255, groups = CreateMarker.class)
    private String name;

    @NotNull(groups = CreateMarker.class)
    @Size(min = 2, max = 255, groups = CreateMarker.class)
    private String author;

    private Boolean available;

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
