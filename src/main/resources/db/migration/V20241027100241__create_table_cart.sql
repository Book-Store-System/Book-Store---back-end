CREATE TABLE cart (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL,
    subtotal NUMERIC DEFAULT 0 NOT NULL
);