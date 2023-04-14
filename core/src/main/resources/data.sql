INSERT INTO app_user(email, password, role)
VALUES
    ('admin@gmail.com', '$2a$10$iOdtg8Qocrhm3pAV85YcWO2VTg5A6RcU2.V.bSpw..aXOeZFRbKtK', 'ROLE_ADMIN'),
    ('user@gmail.com', '$2a$10$iOdtg8Qocrhm3pAV85YcWO2VTg5A6RcU2.V.bSpw..aXOeZFRbKtK', 'ROLE_USER')
; -- password = Password1

INSERT INTO league(name)
VALUES
    ('TIPOS Extraliga'),
    ('NHL')
;

INSERT INTO team(name, app_user_id, league_id)
VALUES
    ('Slovan Bratislava', 2, 1),
    ('Kosice', NULL, 1),
    ('Banska Bystrica', NULL, 1),
    ('Poprad', NULL, 1),
    ('Zvolen', NULL, 1),
    ('Boston Bruins', NULL, 2),
    ('Tampa Bay Lightning', NULL, 2),
    ('Pittsburgh Penguins', NULL, 2),
    ('Dallas Stars', NULL, 2),
    ('Washington Capitals', NULL, 2)
;

INSERT INTO hockey_player(first_name, last_name, date_of_birth, position, skating, physical, shooting, defense, puck_skills, senses, team_id)
VALUES
    ('Mae',	'Bechtelar', '1998-12-12 20:46:24.155688+01', 'attacker', 96, 75, 99, 24, 95, 29, 1 ),
	('Johnathan', 'Rodriguez', '1999-04-01 20:46:24.155688+01', 'attacker', 41, 24, 43, 39, 10, 61, null ),
	('Scottie', 'Heidenreich', '1995-06-25 20:46:24.155688+01', 'center', 49, 55, 82, 91, 56, 53, null ),
	('Devon', 'Bogisich', '1990-04-15 20:46:24.155688+01', 'defender', 13, 14, 79, 77, 99, 95, null ),
	('Deion', 'Raynor', '2000-03-10 20:46:24.155688+01', 'defender', 60, 10, 31, 44, 89, 94, null )
;

INSERT INTO match(date_of_match, home_goals, away_goals, home_team, away_team)
VALUES
    ('2023-04-10 19:10:25-07', 2, 1, 1, 2),
    ('2023-04-11 19:10:25-07', 7, 0, 3, 4),
    ('2023-04-12 19:10:25-07', 1, 6, 5, 2)
;
