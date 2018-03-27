CREATE TABLE roles (
    id INT NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY RL_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO roles (name) VALUES('User'), ('Admin');

CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    username varchar(50) NOT NULL,
    active tinyint(1) NOT NULL DEFAULT true,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY U_nick (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users (username, password)
    VALUES
        /* password is CghsyuFlvsy */
        ('spring-admin', '$2a$11$osLO4.WKa2oj1gYLRW4qcO5L6NRIc1lci/oUyMvo/sXKbWKnjm79y'),
        /* password is Cghsyu>pth */
        ('spring-user', '$2a$11$zxiTilIBmPSQdxUpICktrutvsp1bYw.qfPtiWfhOQllramDYmJMie');

CREATE TABLE users_to_roles (
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (role_id, user_id),
    UNIQUE INDEX role_user_ids (role_id, user_id),
    INDEX fk_users_idx (user_id),
    CONSTRAINT fk_users
    FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_roles
        FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users_to_roles (user_id, role_id)
    VALUES (1, 1), (1, 2), (2, 1);

CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    author varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY BK_book (name, author)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO books (name, author)
    VALUES
        ('John Smith', 'Thinking in Java'),
        ('John Mc Smith', 'Thinking in Java 5th edition');