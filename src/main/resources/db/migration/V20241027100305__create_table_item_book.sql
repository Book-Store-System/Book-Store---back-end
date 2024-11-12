CREATE TABLE item_book (
    id SERIAL PRIMARY KEY NOT NULL,
    book_id INT REFERENCES book (id) NOT NULL,
    cart_id INT REFERENCES cart (id) NOT NULL,
    quantity INT NOT NULL,
    subtotal NUMERIC NOT NULL
);