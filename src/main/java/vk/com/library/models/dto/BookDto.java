package vk.com.library.models.dto;

import vk.com.library.validations.markers.CreateMarker;
import vk.com.library.validations.markers.UpdateMarker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto {
    @NotNull(groups = UpdateMarker.class)
    private Integer id;

    @NotNull(groups = { CreateMarker.class, UpdateMarker.class })
    @Size(min = 5, max = 255, groups = { CreateMarker.class, UpdateMarker.class })
    private String name;

    @NotNull(groups = { CreateMarker.class, UpdateMarker.class })
    @Size(min = 2, max = 255, groups = { CreateMarker.class, UpdateMarker.class })
    private String author;


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
}
