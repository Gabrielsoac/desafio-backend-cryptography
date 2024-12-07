CREATE TABLE purchase(
    id INTEGER PRIMARY KEY NOT NULL,
    user_document BYTEA NOT NULL,
    credit_card_token BYTEA NOT NULL,
    value INTEGER NOT NULL
);