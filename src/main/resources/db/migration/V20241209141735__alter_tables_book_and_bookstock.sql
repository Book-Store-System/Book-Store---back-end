
ALTER TABLE book ADD COLUMN publisher VARCHAR(255) NOT NULL;
ALTER TABLE book ADD COLUMN publication_date DATE NOT NULL;
ALTER TABLE book ADD COLUMN number_of_page INT NOT NULL;
ALTER TABLE book ADD COLUMN dimension VARCHAR(255) NOT NULL;
ALTER TABLE book ADD COLUMN barcode INT NOT NULL;
ALTER TABLE book ADD COLUMN registered_on DATE NOT NULL;

ALTER TABLE book_stock DROP COLUMN publisher;
ALTER TABLE book_stock DROP COLUMN publication_date;
ALTER TABLE book_stock DROP COLUMN number_of_pages;
ALTER TABLE book_stock DROP COLUMN dimensions;
ALTER TABLE book_stock DROP COLUMN barcode;

ALTER TABLE book_stock ADD COLUMN purchase_price NUMERIC NOT NULL;
ALTER TABLE book_stock ADD COLUMN profit_margin  NUMERIC NOT NULL;
ALTER TABLE book_stock ADD COLUMN last_purchase  DATE NOT NULL;
