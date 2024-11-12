ALTER TABLE item_book RENAME COLUMN book_id TO book_stock_id;

ALTER TABLE item_book DROP CONSTRAINT IF EXISTS item_book_book_id_fkey;

ALTER TABLE item_book ADD CONSTRAINT item_book_book_stock_id_fkey FOREIGN KEY (book_stock_id) REFERENCES book_stock(id);