package vk.com.library.models.dto;

import vk.com.library.models.entities.User;

import java.util.Set;

public class BookDto {
    private Integer id;
    private String name;
    private String author;
    private Integer inventory;
    private Boolean availability;
    private Set<User> readers;


}
