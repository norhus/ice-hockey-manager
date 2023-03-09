DROP TABLE IF EXISTS hockey_player_X_ability;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS hockey_player;
DROP TABLE IF EXISTS ability;

CREATE TABLE app_user (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(1024) NOT NULL UNIQUE,
    password VARCHAR(64),
    role VARCHAR(16) NOT NULL
);

CREATE TABLE hockey_player (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    date_of_birth TIMESTAMP NOT NULL,
    position VARCHAR(16) NOT NULL,
    skating INT NOT NULL,
    physical INT NOT NULL,
    shooting INT NOT NULL,
    defense INT NOT NULL,
    puck_skills INT NOT NULL,
    senses INT NOT NULL
);

CREATE TABLE ability (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(1024) NOT NULL UNIQUE
);

CREATE TABLE hockey_player_X_ability (
    hockey_player_id BIGINT NOT NULL REFERENCES hockey_player(id) ON DELETE CASCADE,
    ability_id BIGINT NOT NULL REFERENCES ability(id) ON DELETE CASCADE,
    PRIMARY KEY (hockey_player_id, ability_id)
);
