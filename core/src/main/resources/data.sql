INSERT INTO app_user(email, password, role)
VALUES
    ('admin@gmail.com', '$2a$10$iOdtg8Qocrhm3pAV85YcWO2VTg5A6RcU2.V.bSpw..aXOeZFRbKtK', 'ROLE_ADMIN'),
    ('user@gmail.com', '$2a$10$iOdtg8Qocrhm3pAV85YcWO2VTg5A6RcU2.V.bSpw..aXOeZFRbKtK', 'ROLE_USER')
; -- password = Password1

INSERT INTO hockey_player(first_name, last_name, date_of_birth, "position", skating, physical, shooting, defense, puck_skills, senses)
VALUES
    ('Mae',	'Bechtelar', '2023-03-10 20:46:24.155688+01', 'attacker', 968, 753, 992, 246, 953, 298),
	('Johnathan', 'Rodriguez', '2023-03-10 20:46:24.155688+01', 'attacker', 415, 242, 434, 391, 104, 612),
	('Scottie', 'Heidenreich', '2023-03-10 20:46:24.155688+01', 'attacker', 493, 55, 829, 915, 563, 539),
	('Devon', 'Bogisich', '2023-03-10 20:46:24.155688+01', 'attacker', 134, 140, 793, 728, 997, 951),
	('Deion', 'Raynor', '2023-03-10 20:46:24.155688+01', 'attacker', 604, 109, 318, 441, 893, 943)
;

INSERT INTO league(name)
VALUES ('extraliga')
;

INSERT INTO team(name, app_user_id, league_id)
VALUES
    ('HC Slovan Bratislava', 2, 1),
    ('Kosice', NULL, 1),
    ('Banska Bystrica', NULL, 1),
    ('Poprad', NULL, 1),
    ('Detva', NULL, 1)
;

INSERT INTO match(date_of_match, home_goals, away_goals, home_team, away_team)
VALUES
    ('2023-04-27 19:10:25-07', 2, 1, 1, 2),
    ('2023-04-25 19:10:25-07', 7, 0, 3, 4),
    ('2023-04-22 19:10:25-07', 1, 6, 5, 2)
;