CREATE DATABASE IF NOT EXISTS rewards_db;
USE rewards_db;

DROP TABLE IF EXISTS purchases;

CREATE TABLE purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL
);

INSERT INTO purchases (customer_id, amount, date) VALUES (1, 120.00, '2025-01-15');
INSERT INTO purchases (customer_id, amount, date) VALUES (1, 75.00, '2025-01-20');
INSERT INTO purchases (customer_id, amount, date) VALUES (2, 200.00, '2025-02-10');
INSERT INTO purchases (customer_id, amount, date) VALUES (3, 45.00, '2025-03-05');

select * from purchases;