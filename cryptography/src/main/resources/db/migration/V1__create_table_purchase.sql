CREATE TABLE purchase(
    id SERIAL PRIMARY KEY NOT NULL,
    user_document VARCHAR(255) NOT NULL,
    credit_card_token VARCHAR(255) NOT NULL,
    value INTEGER NOT NULL
);