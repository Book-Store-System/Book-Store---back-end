CREATE TABLE book (
    id SERIAL PRIMARY KEY NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    genero VARCHAR(100) NOT NULL
);