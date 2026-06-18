--liquibase formatted sql

--changeset dmitrij:004-create-transactions
CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,

    sender_account_id UUID NOT NULL,
    receiver_account_id UUID NOT NULL,

    amount NUMERIC(15,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_transaction_sender FOREIGN KEY (sender_account_id)
        REFERENCES accounts(id),

    CONSTRAINT fk_transaction_receiver FOREIGN KEY (receiver_account_id)
        REFERENCES accounts(id)

);