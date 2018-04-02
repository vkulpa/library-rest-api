package vk.com.library.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books_to_users")
public class Reader {
    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reader() {}

    public Reader(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
