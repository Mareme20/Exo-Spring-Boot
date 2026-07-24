-- Script de reference (Hibernate genere le schema via ddl-auto: update).
-- Utile pour une creation manuelle ou une revue par un DBA.

CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL PRIMARY KEY,
    nom             VARCHAR(120)  NOT NULL,
    email           VARCHAR(180)  NOT NULL UNIQUE,
    date_creation   TIMESTAMP     NOT NULL DEFAULT now(),
    date_mise_a_jour TIMESTAMP
);

CREATE TABLE IF NOT EXISTS wallets (
    id              BIGSERIAL PRIMARY KEY,
    solde           NUMERIC(19,4) NOT NULL DEFAULT 0,
    devise          VARCHAR(3)    NOT NULL,
    utilisateur_id  BIGINT        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    date_creation   TIMESTAMP     NOT NULL DEFAULT now(),
    date_mise_a_jour TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    id                BIGSERIAL PRIMARY KEY,
    type              VARCHAR(30)   NOT NULL,
    montant           NUMERIC(19,4) NOT NULL,
    date_transaction  TIMESTAMP     NOT NULL,
    statut            VARCHAR(20)   NOT NULL,
    wallet_id         BIGINT        NOT NULL REFERENCES wallets(id) ON DELETE CASCADE,
    date_creation     TIMESTAMP     NOT NULL DEFAULT now(),
    date_mise_a_jour  TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_wallets_utilisateur_id ON wallets(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_transactions_wallet_id ON transactions(wallet_id);
