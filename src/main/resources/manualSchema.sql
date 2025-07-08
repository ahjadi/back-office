-- Users' table
CREATE TABLE users (
                       id       SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);



-- Customers' table
CREATE TABLE customers (
                           id              SERIAL PRIMARY KEY,
                           customer_number INTEGER      NOT NULL UNIQUE,
                           customer_name   VARCHAR(255) NOT NULL,
                           date_of_birth   DATE         NOT NULL,
                           gender          VARCHAR(1)   NOT NULL
);
