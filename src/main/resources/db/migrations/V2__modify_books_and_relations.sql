CREATE TABLE books_to_users (
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (book_id, user_id),
    UNIQUE INDEX book_record (book_id, user_id)/*,
    INDEX fk_users_idx (user_id),
    CONSTRAINT fk_users
    FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_books
        FOREIGN KEY (book_id)
        REFERENCES books (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE books
ADD COLUMN inventory INT(11) NOT NULL DEFAULT 0 AFTER author;
