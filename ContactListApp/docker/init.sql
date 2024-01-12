CREATE SCHEMA IF NOT EXISTS contacts;

CREATE TABLE IF NOT EXISTS contacts.contact
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone BIGINT NOT NULL
    )