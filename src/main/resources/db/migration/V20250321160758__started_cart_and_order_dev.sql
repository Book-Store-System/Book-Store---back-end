-- Item_cart table

ALTER TABLE item_book RENAME TO item_cart;
ALTER TABLE item_cart ADD COLUMN is_deleted BOOL DEFAULT FALSE;

-- Cart Table

ALTER TABLE cart DROP COLUMN user_id;

-- Address Table

CREATE TABLE address (
    id SERIAL PRIMARY KEY NOT NULL,
    NUMBER INT NOT NULL,
    CEP VARCHAR(50) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    reference VARCHAR(200) NOT NULL,
    address_default BOOL DEFAULT FALSE,
    city VARCHAR(100) NOT NULL,
    state varchar(100) NOT NULL
);

-- User Table

ALTER TABLE users ADD COLUMN address_id INT REFERENCES address (id);


-- Order

CREATE TABLE purchase_order  (
    id SERIAL PRIMARY KEY NOT NULL,
    cart_id INT REFERENCES cart (id) NOT NULL,
    payment VARCHAR(100) NOT NULL,
    shipping NUMERIC NOT NULL,
    total_value NUMERIC NOT NULL
);