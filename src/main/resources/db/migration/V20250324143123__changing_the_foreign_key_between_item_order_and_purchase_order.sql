ALTER TABLE purchase_order DROP COLUMN item_or_id;
ALTER TABLE item_order ADD COLUMN order_id INT REFERENCES purchase_order(id);