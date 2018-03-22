package vk.com.library.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto {
    private Integer id;
    @NotNull
    @Size(min = 5, max = 255)
    private String name;
    @NotNull
    @Size(min = 2, max = 255)
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
