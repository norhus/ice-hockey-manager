DROP TABLE IF EXISTS app_user CASCADE;
DROP TABLE IF EXISTS hockey_player;
DROP TABLE IF EXISTS league CASCADE;
DROP TABLE  IF EXISTS team CASCADE;
DROP TABLE IF EXISTS match;

CREATE TABLE app_user (
    id IDENTITY PRIMARY KEY,
    email VARCHAR(1024) NOT NULL UNIQUE,
    password VARCHAR(64),
    role VARCHAR(16) NOT NULL
);

CREATE TABLE hockey_player (
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    date_of_birth TIMESTAMP WITH TIME ZONE NOT NULL,
    position VARCHAR(16) NOT NULL,
    skating INT NOT NULL,
    physical INT NOT NULL,
    shooting INT NOT NULL,
    defense INT NOT NULL,
    puck_skills INT NOT NULL,
    senses INT NOT NULL
);

CREATE TABLE league (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE team (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    app_user_id BIGINT REFERENCES app_user(id),
    league_id BIGINT REFERENCES league(id) NOT NULL
);

CREATE TABLE match(
    id IDENTITY PRIMARY KEY,
    date_of_match TIMESTAMP,
    home_goals INT,
    away_goals INT,
    home_team BIGINT REFERENCES team(id) NOT NULL,
    away_team BIGINT REFERENCES team(id) NOT NULL
);
