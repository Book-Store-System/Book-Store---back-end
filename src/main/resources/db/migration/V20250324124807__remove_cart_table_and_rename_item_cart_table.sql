ALTER TABLE purchase_order DROP COLUMN cart_id;
ALTER TABLE item_cart DROP COLUMN cart_id;
DROP TABLE IF EXISTS cart;
ALTER TABLE item_cart RENAME TO item_order;
ALTER TABLE purchase_order ADD COLUMN item_Or_id INT REFERENCES item_order (id);