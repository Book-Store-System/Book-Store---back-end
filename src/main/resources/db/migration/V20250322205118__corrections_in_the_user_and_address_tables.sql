ALTER TABLE users DROP COLUMN address_id;

ALTER TABLE address ADD COLUMN user_id INT REFERENCES users (id);
