import json
import random

from faker import Faker
from locust import HttpUser, task, between, TaskSet
from locust.exception import StopUser


class BasicUserBehavior(TaskSet):
    @task(3)
    def get_unplayed_matches(self):
        self.client.get("/api/matches/find-unplayed-matches")

    @task(3)
    def get_tables(self):
        self.client.get("http://localhost:8083/api/tables")

    @task(3)
    def add_player_to_team(self):
        hockey_players = self.get_hockey_players_without_team()
        if len(hockey_players) == 0:
            return

        player_id = random.choice(hockey_players)['id']
        self.add_player(player_id)

    def get_hockey_players_without_team(self):
        hockey_players = None

        with self.client.get("/api/hockey-players/get-all-without-team", catch_response=True) as response:
            if response.status_code == 200:
                hockey_players = response.json()
                response.success()
            else:
                response.failure("Failed to get hockey players without team")
                raise StopUser

        return hockey_players

    def add_player(self, player_id):
        with self.client.put("/api/teams/1/add-hockey-players", catch_response=True, data=json.dumps(
                [
                    player_id
                ]
        )) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure("Failed add hockey player to team")
                raise StopUser


class AdvancedUserBehavior(TaskSet):
    @task(1)
    def get_teams(self):
        self.client.get("/api/teams")

    @task(2)
    def get_team_by_name(self):
        self.client.get("/api/teams/Kosice")

    @task(2)
    def get_teams_by_league_name(self):
        self.client.get("/api/teams/find-by-league/NHL")

    @task(1)
    def get_matches(self):
        self.client.get("/api/matches")

    @task(2)
    def get_matches_by_league(self):
        self.client.get("/api/matches/NHL")

    @task(2)
    def get_unplayed_matches(self):
        self.client.get("/api/matches/find-unplayed-matches")

    @task(1)
    def get_played_matches_by_league(self):
        self.client.get("/api/matches/find-played-matches/TIPOS Extraliga")

    @task(2)
    def get_players(self):
        self.client.get("/api/hockey-players")

    @task(1)
    def get_player_by_id(self):
        self.client.get("/api/hockey-players/2")

    @task(2)
    def get_leagues(self):
        self.client.get("/api/leagues")

    @task(1)
    def get_league_by_name(self):
        self.client.get("/api/leagues/NHL")


class AuthorizedUser(HttpUser):
    host = "http://localhost:8081"
    token = None

    def on_start(self):
        if not AuthorizedUser.token:
            AuthorizedUser.token = input("Please enter token: ")
            print("\nYour token is:", AuthorizedUser.token)
        self.client.headers = {'content-type': 'application/json',
                               'Authorization': f'Bearer {AuthorizedUser.token}'}


class BasicUser(AuthorizedUser):
    weight = 10
    wait_time = between(5, 15)

    tasks = [BasicUserBehavior]


class AdvancedUser(AuthorizedUser):
    weight = 6
    wait_time = between(5, 15)

    tasks = [BasicUserBehavior, AdvancedUserBehavior]


class AdminUser(AuthorizedUser):
    weight = 1
    wait_time = between(60, 3600)

    @task(8)
    def create_player(self):
        fake = Faker()
        with self.client.post("/api/hockey-players", catch_response=True, data=json.dumps(
                {
                    "firstName": fake.first_name(),
                    "lastName": fake.last_name(),
                    "dateOfBirth": "1999-04-01T19:46:24.155688Z",
                    "position": random.choice(['center', 'defender', 'attacker', 'goalkeeper']),
                    "skating": random.randrange(1, 100),
                    "physical": random.randrange(1, 100),
                    "shooting": random.randrange(1, 100),
                    "defense": random.randrange(1, 100),
                    "puckSkills": random.randrange(1, 100),
                    "senses": random.randrange(1, 100),
                    "teamDto": None
                }
        )) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure("Failed create hockey player")
                raise StopUser

    @task(2)
    def delete_player(self):
        hockey_players = None

        with self.client.get("/api/hockey-players", catch_response=True) as response:
            if response.status_code == 200:
                hockey_players = response.json()
                response.success()
            else:
                response.failure("Failed to get hockey players")
                raise StopUser

        if len(hockey_players) == 0:
            return

        with self.client.delete(f"/api/hockey-players/{random.choice(hockey_players)['id']}", catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure("Failed delete hockey player")
                raise StopUser
