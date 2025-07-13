CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

ALTER TABLE users OWNER TO postgres;

CREATE TABLE customers
(
    id              SERIAL PRIMARY KEY,
    customer_number INTEGER      NOT NULL UNIQUE,
    customer_name   VARCHAR(255) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    gender          VARCHAR(1)   NOT NULL
);

ALTER TABLE customers OWNER TO postgres;

-- Insert admin user
INSERT INTO users (username, password)
VALUES ('admin', 'Password123');

-- Insert 10 customers
INSERT INTO customers (customer_number, customer_name, date_of_birth, gender) VALUES
                                                                                  (100123456, 'Fahad Al-Mutairi',    '1992-05-17', 'M'),
                                                                                  (100234567, 'Noura Al-Dabbous',    '1998-11-03', 'F'),
                                                                                  (100345678, 'Yousef Al-Salem',     '1985-09-22', 'M'),
                                                                                  (100456789, 'Dana Al-Fahad',       '1994-03-12', 'F'),
                                                                                  (100567890, 'Abdullah Al-Rashidi', '1990-07-08', 'M'),
                                                                                  (100678901, 'Mariam Al-Kandari',   '1995-01-19', 'F'),
                                                                                  (100789012, 'Khaled Al-Ajmi',      '1988-12-01', 'M'),
                                                                                  (100890123, 'Fatima Al-Otaibi',    '1996-06-15', 'F'),
                                                                                  (100901234, 'Saud Al-Shemmari',    '1993-04-27', 'M'),
                                                                                  (101012345, 'Haya Al-Harbi',       '1999-10-09', 'F');