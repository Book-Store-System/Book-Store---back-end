CREATE TABLE delivery_control (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INT REFERENCES users (id),
    order_id INT REFERENCES purchase_order (id),
    transportation VARCHAR (150),
    order_status VARCHAR (50),
    delivered_on DATE
);

CREATE TABLE cupom (
    id SERIAL PRIMARY KEY NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    title VARCHAR (100) NOT NULL,
    description varchar (150) NOT NULL
);

CREATE TABLE offer (
    id SERIAL PRIMARY KEY NOT NULL,
    description VARCHAR (120) NOT NULL
);

CREATE TABLE feedback (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INT REFERENCES users (id) NOT NULL,
    book_stock_id INT REFERENCES book_stock (id) NOT NULL,
    feedback VARCHAR (255) NOT NULL
);

ALTER TABLE purchase_order ADD COLUMN cupom_id INT REFERENCES cupom (id);
ALTER TABLE book_stock ADD COLUMN offer_id INT REFERENCES offer (id);
