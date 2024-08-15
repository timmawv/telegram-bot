CREATE TABLE users
(
    chatId    BIGINT PRIMARY KEY,
    firstName VARCHAR NOT NULL,
    isActive  BOOLEAN NOT NULL
);