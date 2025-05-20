-- author table
ALTER TABLE book ALTER COLUMN author TYPE INT USING author::integer;

ALTER TABLE book RENAME COLUMN author TO author_id;

ALTER TABLE book ADD CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author (id);

-- category table
ALTER TABLE book RENAME COLUMN genre TO category_id;

ALTER TABLE book ALTER COLUMN category_id TYPE INT USING category_id::integer;

ALTER TABLE book ADD CONSTRAINT fk_book_category FOREIGN KEY (category_id) REFERENCES category (id);


-- publisher table
ALTER TABLE book ALTER COLUMN publisher TYPE INT USING publisher::integer;

ALTER TABLE book RENAME COLUMN publisher TO publisher_id;

ALTER TABLE book ADD CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (id);