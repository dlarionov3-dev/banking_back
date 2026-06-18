--liquibase formatted sql

--changeset dmitrij:003-create-account
CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    account_number VARCHAR(34) NOT NULL UNIQUE,
    currency CHAR(3) NOT NULL,
    balance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);