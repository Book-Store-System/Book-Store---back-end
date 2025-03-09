ALTER TABLE book ADD COLUMN language VARCHAR(100) NOT NULL;
ALTER TABLE book_stock ADD COLUMN publisher VARCHAR(100) NOT NULL;
ALTER TABLE book_stock ADD COLUMN publication_date DATE  NOT NULL;
ALTER TABLE book_stock ADD COLUMN number_of_pages int NOT NULL;
ALTER TABLE book_stock ADD COLUMN dimensions VARCHAR(100) NOT NULL;
ALTER TABLE book_stock ADD COLUMN barcode INT NOT NULL;