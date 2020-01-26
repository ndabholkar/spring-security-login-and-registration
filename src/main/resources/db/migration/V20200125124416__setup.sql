-- DROP DATABASE IF EXISTS demo_dev;
-- CREATE DATABASE demo_dev;

CREATE TABLE IF NOT EXISTS users
(
    id                      UUID PRIMARY KEY NOT NULL,
    username                VARCHAR(100)     NOT NULL,
    password                VARCHAR(100)     NOT NULL,
    account_non_locked      BOOLEAN          NOT NULL,
    account_non_expired     BOOLEAN          NOT NULL,
    credentials_non_expired BOOLEAN          NOT NULL,
    enabled                 BOOLEAN          NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL REFERENCES users(id),
    role_id UUID NOT NULL REFERENCES roles(id),
    UNIQUE (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_authorities
(
    role_id      UUID NOT NULL REFERENCES roles (id),
    authority_id UUID NOT NULL REFERENCES authorities (id),
    UNIQUE (role_id, authority_id)
);

CREATE extension IF NOT EXISTS "uuid-ossp";

INSERT INTO users (id, username, password, account_non_locked, account_non_expired, credentials_non_expired, enabled) VALUES ('a910177e-2af9-11ea-978f-2e728ce88125', 'superuser', '$2y$12$TAw9PeDYa/fvfD3tTK/ncOBtVg0hZjomuz5DOrIE.YaFzy/499gO2', true, true, true, true);
INSERT INTO users (id, username, password, account_non_locked, account_non_expired, credentials_non_expired, enabled) VALUES (uuid_generate_v1(), 'john_smith', '$2y$12$OXh1L21AXPUYafihRP1ca.lt6SCB5kIluhtwEQeIe/TOpoIDYyhvC', true, true, true, true);
INSERT INTO users (id, username, password, account_non_locked, account_non_expired, credentials_non_expired, enabled) VALUES (uuid_generate_v1(), 'jane_doe', '$2y$12$sZX4/iVhLaICDcYefHF6jOGL.dCeRCZFimcWqMgmKizy78HfwmIy.', true, true, true, true);
INSERT INTO users (id, username, password, account_non_locked, account_non_expired, credentials_non_expired, enabled) VALUES (uuid_generate_v1(), 'jack_slater', '$2y$12$v4yErprKMu9jsu7bEB.0.u36bkjtq.XZjSDz9OX8LHVGa9DCtHR5y', true, true, true, true);

INSERT INTO roles (id, name) VALUES ('e12c781e-2af9-11ea-978f-2e728ce88125', 'ADMIN');
INSERT INTO roles (id, name) VALUES (uuid_generate_v1(), 'ROLE_MANAGER');
INSERT INTO roles (id, name) VALUES (uuid_generate_v1(), 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (uuid_generate_v1(), 'ROLE_TRAINEE');

INSERT INTO authorities (id, name) VALUES ('3a663c26-2afa-11ea-85fa-2e728ce88125', 'AUTH_ALL');

INSERT INTO user_roles (user_id, role_id) VALUES ('a910177e-2af9-11ea-978f-2e728ce88125', 'e12c781e-2af9-11ea-978f-2e728ce88125');
INSERT INTO role_authorities (role_id, authority_id) VALUES ('e12c781e-2af9-11ea-978f-2e728ce88125', '3a663c26-2afa-11ea-85fa-2e728ce88125');
