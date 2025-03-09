 CREATE TABLE book_stock (
    id SERIAL PRIMARY KEY NOT NULL,
    book_id INT REFERENCES book (id) NOT NULL,
    qnt_stock INT NOT NULL,
    status VARCHAR(50) DEFAULT 'WITH_STOCK' NOT NULL
);